package co.casterlabs.sdk.twitch.helix;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Collection;
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
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth.TwitchHelixAuthData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@SuppressWarnings("deprecation")
public class TwitchHelixAuth extends AuthProvider<TwitchHelixAuthData> {
    private final ReentrantLock lock = new ReentrantLock();

    private @Getter String clientId;
    private String clientSecret;
    private boolean isApplicationAuth = false;

    /* ---------------- */
    /* Construction     */
    /* ---------------- */

    /**
     * User
     */
    protected TwitchHelixAuth(AuthDataProvider<TwitchHelixAuthData> dataProvider, String clientId, String clientSecret) {
        super(dataProvider);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    /**
     * Application
     */
    protected TwitchHelixAuth(String clientId, String clientSecret) {
        super(new InMemoryAuthDataProvider<>(TwitchHelixAuthData.of(null)));
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.isApplicationAuth = true;
    }

    public static TwitchHelixAuth ofUser(AuthDataProvider<TwitchHelixAuthData> dataProvider, String clientId, String clientSecret) {
        return new TwitchHelixAuth(dataProvider, clientId, clientSecret);
    }

    public static TwitchHelixAuth ofApplication(String clientId, String clientSecret) {
        return new TwitchHelixAuth(clientId, clientSecret);
    }

    /* ---------------- */
    /* Impl.            */
    /* ---------------- */

    @Override
    public void authenticateRequest(@NonNull HttpRequest.Builder request) throws ApiAuthException {
        request.header("Authorization", "Bearer " + this.getAccessToken());
        request.header("Client-ID", this.clientId);
    }

    @Override
    public void refresh() throws ApiAuthException {
        this.lock.lock();
        try {
            QueryBuilder params;
            if (this.isApplicationAuth) {
                params = QueryBuilder.from(
                    "grant_type", "client_credentials",
                    "client_id", this.clientId,
                    "client_secret", this.clientSecret
                );
            } else {
                params = QueryBuilder.from(
                    "grant_type", "refresh_token",
                    "refresh_token", this.data().refreshToken,
                    "client_id", this.clientId,
                    "client_secret", this.clientSecret
                );
            }

            TwitchHelixAuthData data = tokenEndpoint(params);
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
            TwitchHelixAuthData data = this.data();

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
    public static class TwitchHelixAuthData {
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

        public static TwitchHelixAuthData of(String refreshToken) {
            TwitchHelixAuthData d = new TwitchHelixAuthData();
            d.issuedAt = 0;
            d.refreshToken = refreshToken;
            return d;
        }

    }

    /* ---------------- */
    /* Code Grant       */
    /* ---------------- */

    public static String startCodeGrant(@NonNull String clientId, @NonNull String redirectUri, @NonNull Collection<String> scopes, @Nullable String state) {
        return "https://id.twitch.tv/oauth2/authorize?" + QueryBuilder.from(
            "response_type", "code",
            "force_verify", "true",
            "client_id", clientId,
            "redirect_uri", redirectUri,
            "scope", String.join(" ", scopes),
            "state", state
        );
    }

    public static TwitchHelixAuthData exchangeCodeGrant(@NonNull ParsedQuery query, @NonNull String clientId, @NonNull String clientSecret, @NonNull String redirectUri) throws ApiAuthException {
        return tokenEndpoint(
            QueryBuilder.from(
                "grant_type", "authorization_code",
                "code", query.getSingle("code"),
                "client_id", clientId,
                "client_secret", clientSecret,
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

    private static TwitchHelixAuthData tokenEndpoint(QueryBuilder params) throws ApiAuthException {
        try {
            JsonObject json = WebRequest.sendHttpRequest(
                HttpRequest.newBuilder()
                    .uri(URI.create("https://id.twitch.tv/oauth2/token"))
                    .POST(BodyPublishers.ofString(params.toString()))
                    .header("Content-Type", "application/x-www-form-urlencoded"),
                RsonBodyHandler.of(JsonObject.class),
                null
            ).body();
            checkAndThrow(json);

            if (json.containsKey("scope")) {
                // Twitch's response payload always sends scope back as an array rather than a
                // string. So we parse it and replace it.
                json.put(
                    "scope",
                    String.join(" ", Rson.DEFAULT.fromJson(json.get("scope"), String[].class))
                );
            }

            return Rson.DEFAULT.fromJson(json, TwitchHelixAuthData.class);
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

}
