package co.casterlabs.sdk.youtube;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Collection;
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
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.youtube.YoutubeAuth.YoutubeAuthData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@SuppressWarnings("deprecation")
public class YoutubeAuth extends AuthProvider<YoutubeAuthData> {
    private final ReentrantLock lock = new ReentrantLock();

    private @Getter String apiKey;
    private @Getter @Nullable String clientId; // Null when application auth.
    private @Nullable String clientSecret; // Null when application auth.
    private boolean isApplicationAuth = false;

    /* ---------------- */
    /* Construction     */
    /* ---------------- */

    /**
     * User
     */
    protected YoutubeAuth(AuthDataProvider<YoutubeAuthData> dataProvider, String apiKey, String clientId, String clientSecret) {
        super(dataProvider);
        this.apiKey = apiKey;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    /**
     * Application
     */
    protected YoutubeAuth(String apiKey) {
        super(new InMemoryAuthDataProvider<>(YoutubeAuthData.of(null)));
        this.apiKey = apiKey;
        this.isApplicationAuth = true;
    }

    public static YoutubeAuth ofUser(AuthDataProvider<YoutubeAuthData> dataProvider, String apiKey, String clientId, String clientSecret) {
        return new YoutubeAuth(dataProvider, apiKey, clientId, clientSecret);
    }

    public static YoutubeAuth ofApplication(String apiKey) {
        return new YoutubeAuth(apiKey);
    }

    /* ---------------- */
    /* Impl.            */
    /* ---------------- */

    @Override
    public void authenticateRequest(@NonNull HttpRequest.Builder request) throws ApiAuthException {
        if (!this.isApplicationAuth()) {
            request.header("Authorization", "Bearer " + this.getAccessToken());
        }

        // Inject the client key.
        String uri = request.build().uri().toString();
        if (uri.indexOf('?') == -1) {
            request.uri(URI.create(uri + "?key=" + this.apiKey));
        } else {
            request.uri(URI.create(uri + "&key=" + this.apiKey));
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

            YoutubeAuthData data = tokenEndpoint(body, refreshToken);
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
            YoutubeAuthData data = this.data();

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
    public static class YoutubeAuthData {
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

        public static YoutubeAuthData of(String refreshToken) {
            YoutubeAuthData d = new YoutubeAuthData();
            d.issuedAt = 0;
            d.refreshToken = refreshToken;
            return d;
        }

    }

    /* ---------------- */
    /* Code Grant       */
    /* ---------------- */

    public static String startCodeGrant(@NonNull String clientId, @NonNull String redirectUri, @NonNull Collection<String> scopes, @Nullable String state) {
        return "https://accounts.google.com/o/oauth2/v2/auth?" + QueryBuilder.from(
            "response_type", "code",
            "prompt", "consent",
            "access_type", "offline",
            "client_id", clientId,
            "redirect_uri", redirectUri,
            "scope", String.join(" ", scopes),
            "state", state
        );
    }

    public static YoutubeAuthData exchangeCodeGrant(@NonNull ParsedQuery query, @NonNull String clientId, @NonNull String clientSecret, @NonNull String redirectUri) throws ApiAuthException {
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

    private static YoutubeAuthData tokenEndpoint(QueryBuilder params, String oldRefreshToken) throws ApiAuthException {
        try {
            HttpResponse<JsonObject> response = WebRequest.sendHttpRequest(
                HttpRequest
                    .newBuilder()
                    .uri(URI.create("https://oauth2.googleapis.com/token"))
                    .POST(BodyPublishers.ofString(params.toString()))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("X-Client-Type", "api"),
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

            return Rson.DEFAULT.fromJson(json, YoutubeAuthData.class);
        } catch (IOException | ApiException e) {
            throw new ApiAuthException(e);
        }
    }

}
