package co.casterlabs.sdk.tiktok;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthDataProvider;
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
import co.casterlabs.sdk.tiktok.TiktokAuth.TiktokAuthData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@SuppressWarnings("deprecation")
public class TiktokAuth extends AuthProvider<TiktokAuthData> {
    private final ReentrantLock lock = new ReentrantLock();

    private @Getter String clientKey;
    private String clientSecret;

    /* ---------------- */
    /* Construction     */
    /* ---------------- */

    /**
     * User
     */
    protected TiktokAuth(AuthDataProvider<TiktokAuthData> dataProvider, String clientKey, String clientSecret) {
        super(dataProvider);
        this.clientKey = clientKey;
        this.clientSecret = clientSecret;
    }

    public static TiktokAuth ofUser(AuthDataProvider<TiktokAuthData> dataProvider, String clientKey, String clientSecret) {
        return new TiktokAuth(dataProvider, clientKey, clientSecret);
    }

    /* ---------------- */
    /* Impl.            */
    /* ---------------- */

    @Override
    public void authenticateRequest(@NonNull HttpRequest.Builder request) throws ApiAuthException {
        request.header("Authorization", "Bearer " + this.getAccessToken());
    }

    @Override
    public void refresh() throws ApiAuthException {
        this.lock.lock();
        try {
            QueryBuilder params = QueryBuilder.from(
                "grant_type", "refresh_token",
                "refresh_token", this.data().refreshToken,
                "client_key", this.clientKey,
                "client_secret", this.clientSecret
            );

            TiktokAuthData data = tokenEndpoint(params);
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
        return false;
    }

    @Override
    public boolean isExpired() {
        this.lock.lock();
        try {
            TiktokAuthData data = this.data();

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
    public static class TiktokAuthData {
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

        public static TiktokAuthData of(String refreshToken) {
            TiktokAuthData d = new TiktokAuthData();
            d.issuedAt = 0;
            d.refreshToken = refreshToken;
            return d;
        }

    }

    /* ---------------- */
    /* Code Grant       */
    /* ---------------- */

    public static String startCodeGrant(@NonNull String clientKey, @NonNull String redirectUri, @NonNull Collection<String> scopes, @Nullable String state) {
        return "https://www.tiktok.com/v2/auth/authorize?" + QueryBuilder.from(
            "response_type", "code",
            "client_key", clientKey,
            "redirect_uri", redirectUri,
            "scope", String.join(" ", scopes),
            "state", state
        );
    }

    public static TiktokAuthData exchangeCodeGrant(@NonNull ParsedQuery query, @NonNull String clientKey, @NonNull String clientSecret, @NonNull String redirectUri) throws ApiAuthException {
        return tokenEndpoint(
            QueryBuilder.from(
                "grant_type", "authorization_code",
                "code", query.getSingle("code"),
                "client_key", clientKey,
                "client_secret", clientSecret,
                "redirect_uri", redirectUri
            )
        );
    }

    /* ---------------- */
    /* Util             */
    /* ---------------- */

    private static void checkAndThrow(JsonObject body) throws ApiAuthException {
        if (body.containsKey("error_code")) {
            int code = body.getNumber("error_code").intValue();

            if (code != 0) {
                throw new ApiAuthException(body.toString());
            }
        }
    }

    private static TiktokAuthData tokenEndpoint(QueryBuilder params) throws ApiAuthException {
        try {
            JsonObject json = WebRequest.sendHttpRequest(
                HttpRequest.newBuilder()
                    .uri(URI.create(TiktokApi.TIKTOK_OPENAPI_URL + "/v2/oauth/token/"))
                    .POST(BodyPublishers.ofString(params.toString()))
                    .header("Content-Type", "application/x-www-form-urlencoded"),
                RsonBodyHandler.of(JsonObject.class),
                null
            ).body();
            checkAndThrow(json);

            return Rson.DEFAULT.fromJson(json, TiktokAuthData.class);
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

}
