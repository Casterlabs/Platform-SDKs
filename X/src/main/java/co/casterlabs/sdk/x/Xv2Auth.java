package co.casterlabs.sdk.x;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Base64;
import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthDataProvider;
import co.casterlabs.apiutil.auth.AuthDataProvider.InMemoryAuthDataProvider;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.apiutil.auth.PKCEUtil;
import co.casterlabs.apiutil.auth.PKCEUtil.ChallengeMethod;
import co.casterlabs.apiutil.web.ParsedQuery;
import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.x.Xv2Auth.XAuthData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@SuppressWarnings("deprecation")
public class Xv2Auth extends AuthProvider<XAuthData> {
    private final ReentrantLock lock = new ReentrantLock();

    private @Getter String clientId;
    private String clientSecret;

    private boolean isApplicationAuth;

    /**
     * User
     */
    protected Xv2Auth(AuthDataProvider<XAuthData> dataProvider, String clientId, String clientSecret) {
        super(dataProvider);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.isApplicationAuth = false;
    }

    /**
     * Application
     */
    protected Xv2Auth(String clientId, String clientSecret) {
        super(new InMemoryAuthDataProvider<>(XAuthData.of(null)));
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.isApplicationAuth = true;
    }

    public static Xv2Auth ofUser(AuthDataProvider<XAuthData> dataProvider, String clientId, String clientSecret) {
        return new Xv2Auth(dataProvider, clientId, clientSecret);
    }

    public static Xv2Auth ofApplication(String clientId, String clientSecret) {
        return new Xv2Auth(clientId, clientSecret);
    }

    @Override
    public void authenticateRequest(@NonNull HttpRequest.Builder request) throws ApiAuthException {
        request.setHeader("Authorization", "Bearer " + this.getAccessToken());
    }

    public String getAccessToken() throws ApiAuthException {
        this.lock.lock();
        try {
            if (this.isExpired()) {
                this.refresh();
            }
            return this.data().accessToken;
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void refresh() throws ApiAuthException {
        this.lock.lock();
        try {
            XAuthData data;
            if (this.isApplicationAuth) {
                QueryBuilder params = QueryBuilder.from("grant_type", "client_credentials");
                data = tokenEndpoint(params, null, this.clientId, this.clientSecret);
            } else {
                String refreshToken = this.data().refreshToken;
                QueryBuilder params = QueryBuilder.from(
                    "grant_type", "refresh_token",
                    "refresh_token", refreshToken,
                    "client_id", this.clientId,
                    "client_secret", this.clientSecret
                );
                data = tokenEndpoint(params, refreshToken, this.clientId, this.clientSecret);
            }

            this.dataProvider.save(data);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean isExpired() {
        this.lock.lock();
        try {
            XAuthData data = this.data();

            if (data.accessToken == null) {
                return true;
            }

            long secondsSinceIssuance = (System.currentTimeMillis() - data.issuedAt) / 1000;
            return secondsSinceIssuance > data.expiresIn;
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean isApplicationAuth() {
        return this.isApplicationAuth;
    }

    @EqualsAndHashCode
    @NoArgsConstructor
    @JsonClass(exposeAll = true)
    public static class XAuthData {
        @JsonField("issued_at")
        public long issuedAt = System.currentTimeMillis();

        @JsonField("access_token")
        public String accessToken;

        @JsonField("expires_in")
        public int expiresIn;

        @JsonField("refresh_token")
        public String refreshToken;

        public String scope;

        @JsonField("token_type")
        public String tokenType;

        public static XAuthData of(String refreshToken) {
            XAuthData d = new XAuthData();
            d.issuedAt = 0;
            d.refreshToken = refreshToken;
            return d;
        }

    }

    /* ---------------- */
    /* Code Grant       */
    /* ---------------- */

    public static String startCodeGrant(@NonNull String clientId, @NonNull String redirectUri, @NonNull Collection<String> scopes, @NonNull String state, @NonNull String verifier) {
        final ChallengeMethod codeChallengeMethod = ChallengeMethod.SHA256;
        String codeChallenge = PKCEUtil.generateChallenge(codeChallengeMethod, verifier);

        return "https://x.com/i/oauth2/authorize?" + QueryBuilder.from(
            "client_id", clientId,
            "response_type", "code",
            "redirect_uri", redirectUri,
            "state", state,
            "scope", String.join(" ", scopes),
            "code_challenge_method", codeChallengeMethod.toString(),
            "code_challenge", codeChallenge
        );
    }

    public static XAuthData exchangeCodeGrant(@NonNull ParsedQuery query, @NonNull String clientId, @NonNull String clientSecret, @NonNull String redirectUri, @NonNull String verifier) throws ApiAuthException {
        return tokenEndpoint(
            QueryBuilder.from(
                "code", query.getSingle("code"),
                "redirect_uri", redirectUri,
                "grant_type", "authorization_code",
                "code_verifier", verifier
            ),
            null, clientId, clientSecret
        );
    }

    /* ---------------- */
    /* Utils            */
    /* ---------------- */

    private static void checkAndThrow(JsonObject body) throws ApiAuthException {
        if (body.containsKey("error") || body.containsKey("errors")) {
            throw new ApiAuthException(body.toString());
        }
    }

    private static XAuthData tokenEndpoint(QueryBuilder params, String oldRefreshToken, String basicUser, String basicPassword) throws ApiAuthException {
        try {
            HttpRequest.Builder request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.x.com/2/oauth2/token"))
                .POST(BodyPublishers.ofString(params.toString()))
                .header("Content-Type", "application/x-www-form-urlencoded");

            if (basicUser != null && basicPassword != null) {
                String basicAuth = basicUser + ":" + basicPassword;
                String encodedAuth = Base64.getEncoder().encodeToString(basicAuth.getBytes());
                request.header("Authorization", "Basic " + encodedAuth);
            }

            JsonObject json = WebRequest.sendHttpRequest(
                request,
                RsonBodyHandler.of(JsonObject.class),
                null
            ).body();
            checkAndThrow(json);

            if (json.containsKey("scope") && json.get("scope").isJsonArray()) {
                json.put(
                    "scope",
                    String.join(" ", Rson.DEFAULT.fromJson(json.get("scope"), String[].class))
                );
            }

            if (!json.containsKey("refresh_token") && oldRefreshToken != null) {
                // Server didn't give us a new refresh token, inject the old one so that we
                // don't break things.
                json.put("refresh_token", oldRefreshToken);
            }

            return Rson.DEFAULT.fromJson(json, XAuthData.class);
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

}
