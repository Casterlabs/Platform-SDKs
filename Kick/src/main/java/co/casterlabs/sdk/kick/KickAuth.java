package co.casterlabs.sdk.kick;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.concurrent.locks.ReentrantLock;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthDataProvider;
import co.casterlabs.apiutil.auth.AuthDataProvider.InMemoryAuthDataProvider;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.KickAuth.KickAuthData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@SuppressWarnings("deprecation")
public class KickAuth extends AuthProvider<KickAuthData> {
    private final ReentrantLock lock = new ReentrantLock();

    private @Getter String clientId;
    private String clientSecret;

    private boolean isApplicationAuth;

    /**
     * User
     */
    protected KickAuth(AuthDataProvider<KickAuthData> dataProvider, String clientId, String clientSecret) {
        super(dataProvider);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.isApplicationAuth = false;
    }

    /**
     * Application
     */
    protected KickAuth(String clientId, String clientSecret) {
        super(new InMemoryAuthDataProvider<>(KickAuthData.of(null)));
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.isApplicationAuth = true;
    }

    public static KickAuth ofUser(AuthDataProvider<KickAuthData> dataProvider, String clientId, String clientSecret) {
        return new KickAuth(dataProvider, clientId, clientSecret);
    }

    public static KickAuth ofApplication(String clientId, String clientSecret) {
        return new KickAuth(clientId, clientSecret);
    }

    @Override
    public void authenticateRequest(@NonNull HttpRequest.Builder request) throws ApiAuthException {
        request.header("Authorization", "Bearer " + this.getAccessToken());
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
            QueryBuilder params = new QueryBuilder();

            if (this.isApplicationAuth) {
                params
                    .put("grant_type", "client_credentials")
                    .put("client_id", this.clientId)
                    .put("client_secret", this.clientSecret);
            } else {
                params
                    .put("grant_type", "refresh_token")
                    .put("refresh_token", this.data().refreshToken)
                    .put("client_id", this.clientId)
                    .put("client_secret", this.clientSecret);
            }

            KickAuthData data = tokenEndpoint(params);
            this.dataProvider.save(data);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean isExpired() {
        this.lock.lock();
        try {
            KickAuthData data = this.data();

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
    public static class KickAuthData {
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

        public static KickAuthData of(String refreshToken) {
            KickAuthData d = new KickAuthData();
            d.issuedAt = 0;
            d.refreshToken = refreshToken;
            return d;
        }

    }

    /* ---------------- */
    /* Utils            */
    /* ---------------- */

    private static void checkAndThrow(JsonObject body) throws ApiAuthException {
        if (body.containsKey("error") || body.containsKey("errors")) {
            throw new ApiAuthException(body.toString());
        }
    }

    static KickAuthData tokenEndpoint(QueryBuilder params) throws ApiAuthException {
        try {
            JsonObject json = WebRequest.sendHttpRequest(
                HttpRequest.newBuilder()
                    .uri(URI.create("https://id.kick.com/oauth/token"))
                    .POST(
                        BodyPublishers.ofString(params.toString())
                    )
                    .header("Content-Type", "application/x-www-form-urlencoded"),
                RsonBodyHandler.of(JsonObject.class),
                null
            ).body();
            checkAndThrow(json);

            if (json.containsKey("scope") && !json.get("scope").isJsonArray()) {
                json.put(
                    "scope",
                    String.join(" ", Rson.DEFAULT.fromJson(json.get("scope"), String[].class))
                );
            }

            return Rson.DEFAULT.fromJson(json, KickAuthData.class);
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

}
