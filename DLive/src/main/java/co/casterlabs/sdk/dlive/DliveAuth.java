package co.casterlabs.sdk.dlive;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Base64;
import java.util.concurrent.locks.ReentrantLock;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthDataProvider;
import co.casterlabs.apiutil.auth.AuthDataProvider.InMemoryAuthDataProvider;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.apiutil.web.ParsedQuery;
import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.dlive.DliveAuth.DliveAuthData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@SuppressWarnings("deprecation")
public class DliveAuth extends AuthProvider<DliveAuthData> {
    private final ReentrantLock lock = new ReentrantLock();

    private @Getter String clientId;
    private String clientSecret;
    private @Nullable String redirectUri; // Null when application auth.
    private boolean isApplicationAuth = false;

    /* ---------------- */
    /* Construction     */
    /* ---------------- */

    /**
     * User
     */
    protected DliveAuth(AuthDataProvider<DliveAuthData> dataProvider, String clientId, String clientSecret, String redirectUri) {
        super(dataProvider);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    /**
     * Application
     */
    protected DliveAuth(String clientId, String clientSecret) {
        super(new InMemoryAuthDataProvider<>(DliveAuthData.of(null)));
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.isApplicationAuth = true;
    }

    public static DliveAuth ofUser(AuthDataProvider<DliveAuthData> dataProvider, String clientId, String clientSecret, String redirectUri) {
        return new DliveAuth(dataProvider, clientId, clientSecret, redirectUri);
    }

    public static DliveAuth ofApplication(String clientId, String clientSecret) {
        return new DliveAuth(clientId, clientSecret);
    }

    /* ---------------- */
    /* Impl.            */
    /* ---------------- */

    @Override
    public void authenticateRequest(@NonNull HttpRequest.Builder request) throws ApiAuthException {
        request.header("Authorization", this.getAccessToken());
    }

    @Override
    public void refresh() throws ApiAuthException {
        this.lock.lock();
        try {
            QueryBuilder params;
            if (this.isApplicationAuth) {
                params = QueryBuilder.from(
                    "grant_type", "client_credentials"
                );
            } else {
                params = QueryBuilder.from(
                    "grant_type", "refresh_token",
                    "refresh_token", this.data().refreshToken,
                    "redirect_uri", this.redirectUri
                );
            }

            DliveAuthData data = tokenEndpoint(this.clientId, this.clientSecret, params);
            this.dataProvider.save(data);
        } finally {
            this.lock.unlock();
        }
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
    public boolean isApplicationAuth() {
        return this.isApplicationAuth;
    }

    @Override
    public boolean isExpired() {
        this.lock.lock();
        try {
            DliveAuthData data = this.data();

            if (data.accessToken == null) {
                return true;
            }

            long secondsSinceIssuance = (System.currentTimeMillis() - data.issuedAt) / 1000;
            return secondsSinceIssuance > data.expiresIn;
        } finally {
            this.lock.unlock();
        }
    }

    @EqualsAndHashCode
    @NoArgsConstructor
    @JsonClass(exposeAll = true)
    public static class DliveAuthData {
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

        @JsonDeserializationMethod("refreshToken")
        private void $deserialize_refreshToken(JsonElement e) {
            this.refreshToken = e.getAsString(); // Compat. for some old code.
        }

        public static DliveAuthData of(String refreshToken) {
            DliveAuthData d = new DliveAuthData();
            d.issuedAt = 0;
            d.refreshToken = refreshToken;
            return d;
        }

    }

    /* ---------------- */
    /* Code Grant       */
    /* ---------------- */

    public static String startCodeGrant(@NonNull String clientId, @NonNull String redirectUri, @NonNull String[] scopes, @Nullable String state) {
        return "https://dlive.tv/o/authorize?" + QueryBuilder.from(
            "response_type", "code",
            "force_verify", "true",
            "client_id", clientId,
            "redirect_uri", redirectUri,
            "scope", String.join(" ", scopes),
            "state", state
        );
    }

    public static DliveAuthData exchangeCodeGrant(@NonNull ParsedQuery query, @NonNull String clientId, @NonNull String clientSecret, @NonNull String redirectUri) throws ApiAuthException {
        return tokenEndpoint(
            clientId,
            clientSecret,
            QueryBuilder.from(
                "grant_type", "authorization_code",
                "code", query.getSingle("code"),
                "redirect_uri", redirectUri
            )
        );
    }

    /* ---------------- */
    /* Util             */
    /* ---------------- */

    private static void checkAndThrow(JsonObject body) throws ApiAuthException {
        if (body.containsKey("error") || body.containsKey("errors")) {
            throw new ApiAuthException(body.toString());
        }
    }

    private static DliveAuthData tokenEndpoint(String clientId, String clientSecret, QueryBuilder params) throws ApiAuthException {
        try {
            String credential = "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

            JsonObject json = WebRequest.sendHttpRequest(
                HttpRequest.newBuilder()
                    .uri(URI.create("https://dlive.tv/o/token"))
                    .header("Authorization", credential)
                    .POST(BodyPublishers.ofString(params.toString()))
                    .header("Content-Type", "application/x-www-form-urlencoded"),
                RsonBodyHandler.of(JsonObject.class),
                null
            ).body();
            checkAndThrow(json);

            return Rson.DEFAULT.fromJson(json, DliveAuthData.class);
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

}
