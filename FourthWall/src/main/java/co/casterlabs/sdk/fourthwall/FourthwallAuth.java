package co.casterlabs.sdk.fourthwall;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.concurrent.locks.ReentrantLock;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthDataProvider;
import co.casterlabs.apiutil.auth.AuthDataProvider.InMemoryAuthDataProvider;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.ParsedQuery;
import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.fourthwall.FourthwallAuth.FourthwallAuthData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@SuppressWarnings("deprecation")
public class FourthwallAuth extends AuthProvider<FourthwallAuthData> {
    private final ReentrantLock lock = new ReentrantLock();

    private @Nullable String basic; // Null when user auth.

    private @Getter @Nullable String clientId; // Null when application auth.
    private @Nullable String clientSecret; // Null when application auth.
    private boolean isApplicationAuth = false;

    /* ---------------- */
    /* Construction     */
    /* ---------------- */

    /**
     * User
     */
    protected FourthwallAuth(AuthDataProvider<FourthwallAuthData> dataProvider, String clientId, String clientSecret) {
        super(dataProvider);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    /**
     * Application
     */
    protected FourthwallAuth(String username, String password) {
        super(new InMemoryAuthDataProvider<>(FourthwallAuthData.of(null)));
        this.basic = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        this.isApplicationAuth = true;
    }

    public static FourthwallAuth ofUser(AuthDataProvider<FourthwallAuthData> dataProvider, String clientId, String clientSecret) {
        return new FourthwallAuth(dataProvider, clientId, clientSecret);
    }

    public static FourthwallAuth ofApplication(String username, String password) {
        return new FourthwallAuth(username, password);
    }

    /* ---------------- */
    /* Impl.            */
    /* ---------------- */

    @Override
    public void authenticateRequest(@NonNull HttpRequest.Builder request) throws ApiAuthException {
        request.setHeader("Accept-Encoding", "identity");

        if (this.isApplicationAuth()) {
            request.setHeader("Authorization", "Basic " + this.basic);
        } else {
            request.setHeader("Authorization", "Bearer " + this.getAccessToken());
        }
    }

    @Override
    public void refresh() throws ApiAuthException {
        if (this.isApplicationAuth) return;

        this.lock.lock();
        try {
            String refreshToken = this.data().refreshToken;

            QueryBuilder body = QueryBuilder.from(
                "grant_type", "refresh_token",
                "refresh_token", refreshToken,
                "client_id", this.clientId,
                "client_secret", this.clientSecret
            );

            FourthwallAuthData data = tokenEndpoint(body, refreshToken);
            this.dataProvider.save(data);
        } finally {
            this.lock.unlock();
        }
    }

    public String getAccessToken() throws ApiAuthException {
        if (this.isApplicationAuth) return null;
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
            FourthwallAuthData data = this.data();

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
    public static class FourthwallAuthData {
        @JsonField("issued_at")
        public long issuedAt = System.currentTimeMillis();

        @JsonField("access_token")
        public String accessToken;

        @JsonField("expires_in")
        public int expiresIn;

        @JsonField("refresh_token")
        public String refreshToken;

        @JsonField("token_type")
        public String tokenType;

        public static FourthwallAuthData of(String refreshToken) {
            FourthwallAuthData d = new FourthwallAuthData();
            d.issuedAt = 0;
            d.refreshToken = refreshToken;
            return d;
        }

    }

    /* ---------------- */
    /* Code Grant       */
    /* ---------------- */

    public static String startCodeGrant(@NonNull String clientId, @NonNull String redirectUri, @Nullable String state) {
        return "https://my-shop.fourthwall.com/admin/platform-apps/" + clientId + "/connect?" + QueryBuilder.from(
            "redirect_uri", redirectUri,
            "state", state
        );
    }

    public static FourthwallAuthData exchangeCodeGrant(@NonNull ParsedQuery query, @NonNull String clientId, @NonNull String clientSecret, @NonNull String redirectUri) throws ApiAuthException {
        return tokenEndpoint(
            QueryBuilder.from(
                "grant_type", "authorization_code",
                "code", query.getSingle("code"),
                "client_id", clientId,
                "client_secret", clientSecret,
                "redirect_uri", redirectUri
            ),
            null
        );
    }

    /* ---------------- */
    /* Util             */
    /* ---------------- */

    private static FourthwallAuthData tokenEndpoint(QueryBuilder params, String oldRefreshToken) throws ApiAuthException {
        try {
            HttpResponse<JsonObject> response = WebRequest.sendHttpRequest(
                HttpRequest
                    .newBuilder()
                    .uri(URI.create("https://api.fourthwall.com/open-api/v1.0/platform/token"))
                    .POST(BodyPublishers.ofString(params.toString()))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "identity"),
                RsonBodyHandler.of(JsonObject.class),
                null
            );
            JsonObject json = response.body();

            if (response.statusCode() < 200 && response.statusCode() > 299) {
                // Error!
                if (response.statusCode() == 401) {
                    throw new ApiAuthException(json.toString());
                } else {
                    throw new ApiException(json.toString());
                }
            }
            // Success!

            if (!json.containsKey("refresh_token") && oldRefreshToken != null) {
                // Server didn't give us a new refresh token, inject the old one so that we
                // don't break things.
                json.put("refresh_token", oldRefreshToken);
            }

            return Rson.DEFAULT.fromJson(json, FourthwallAuthData.class);
        } catch (IOException | ApiException e) {
            throw new ApiAuthException(e);
        }
    }

}
