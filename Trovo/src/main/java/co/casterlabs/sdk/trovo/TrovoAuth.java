package co.casterlabs.sdk.trovo;

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
import co.casterlabs.sdk.trovo.TrovoAuth.TrovoAuthData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@SuppressWarnings("deprecation")
public class TrovoAuth extends AuthProvider<TrovoAuthData> {
    private final ReentrantLock lock = new ReentrantLock();

    private @Getter String clientId;
    private @Nullable String clientSecret; // Null when application auth.
    private boolean isApplicationAuth = false;

    /* ---------------- */
    /* Construction     */
    /* ---------------- */

    /**
     * User
     */
    protected TrovoAuth(AuthDataProvider<TrovoAuthData> dataProvider, String clientId, String clientSecret) {
        super(dataProvider);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    /**
     * Application
     */
    protected TrovoAuth(String clientId) {
        super(new InMemoryAuthDataProvider<>(TrovoAuthData.of(null)));
        this.clientId = clientId;
        this.isApplicationAuth = true;
    }

    public static TrovoAuth ofUser(AuthDataProvider<TrovoAuthData> dataProvider, String clientId, String clientSecret) {
        return new TrovoAuth(dataProvider, clientId, clientSecret);
    }

    public static TrovoAuth ofApplication(String clientId) {
        return new TrovoAuth(clientId);
    }

    /* ---------------- */
    /* Impl.            */
    /* ---------------- */

    @Override
    public void authenticateRequest(@NonNull HttpRequest.Builder request) throws ApiAuthException {
        if (!this.isApplicationAuth) {
            request.header("Authorization", "OAuth " + this.getAccessToken());
        }
        request.header("Client-ID", this.clientId);
    }

    @Override
    public void refresh() throws ApiAuthException {
        if (this.isApplicationAuth) return;

        this.lock.lock();
        try {
            JsonObject body = new JsonObject()
                .put("grant_type", "refresh_token")
                .put("client_secret", this.clientSecret)
                .put("refresh_token", this.data().refreshToken);

            JsonObject authData = WebRequest.sendHttpRequest(
                HttpRequest.newBuilder()
                    .uri(URI.create("https://open-api.trovo.live/openplatform/refreshtoken"))
                    .POST(BodyPublishers.ofString(body.toString()))
                    .header("Client-ID", this.clientId)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json"),
                RsonBodyHandler.of(JsonObject.class),
                null
            ).body();
            checkAndThrow(authData);

            // Replace.
            String scope = getScope(this.clientId, authData.getString("access_token"));
            authData.put("scope", scope);

            TrovoAuthData data = Rson.DEFAULT.fromJson(authData, TrovoAuthData.class);
            this.dataProvider.save(data);
        } catch (IOException e) {
            throw new ApiAuthException(e);
        } finally {
            this.lock.unlock();
        }
    }

    public JsonObject getChatToken() throws ApiAuthException, IOException, ApiException {
        return WebRequest.sendHttpRequest(
            HttpRequest.newBuilder(URI.create("https://open-api.trovo.live/openplatform/chat/token")),
            RsonBodyHandler.of(JsonObject.class),
            this
        ).body();
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
            TrovoAuthData data = this.data();

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
    public static class TrovoAuthData {
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

        public static TrovoAuthData of(String refreshToken) {
            TrovoAuthData d = new TrovoAuthData();
            d.issuedAt = 0;
            d.refreshToken = refreshToken;
            return d;
        }

    }

    /* ---------------- */
    /* Code Grant       */
    /* ---------------- */

    public static String startCodeGrant(@NonNull String clientId, @NonNull String redirectUri, @NonNull Collection<String> scopes, @Nullable String state) {
        return "https://open.trovo.live/page/login.html?" + QueryBuilder.from(
            "response_type", "code",
            "client_id", clientId,
            "redirect_uri", redirectUri,
            "scope", String.join("+", scopes),
            "state", state
        );
    }

    public static TrovoAuthData exchangeCodeGrant(@NonNull ParsedQuery query, @NonNull String clientId, @NonNull String clientSecret, @NonNull String redirectUri) throws ApiAuthException {
        try {
            JsonObject body = new JsonObject()
                .put("grant_type", "authorization_code")
                .put("code", query.getSingle("code"))
                .put("client_secret", clientSecret)
                .put("redirect_uri", redirectUri);

            JsonObject authData = WebRequest.sendHttpRequest(
                HttpRequest.newBuilder()
                    .uri(URI.create("https://open-api.trovo.live/openplatform/exchangetoken"))
                    .POST(BodyPublishers.ofString(body.toString()))
                    .header("Client-ID", clientId)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json"),
                RsonBodyHandler.of(JsonObject.class),
                null
            ).body();
            checkAndThrow(authData);

            // Replace.
            String scope = getScope(clientId, authData.getString("access_token"));
            authData.put("scope", scope);

            return Rson.DEFAULT.fromJson(authData, TrovoAuthData.class);
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    /* ---------------- */
    /* Util             */
    /* ---------------- */

    private static String getScope(String clientId, String accessToken) throws ApiAuthException {
        try {
            JsonObject validationData = WebRequest.sendHttpRequest(
                HttpRequest.newBuilder()
                    .uri(URI.create("https://open-api.trovo.live/openplatform/validate"))
                    .header("Authorization", "OAuth " + accessToken)
                    .header("Client-ID", clientId)
                    .header("Accept", "application/json"),
                RsonBodyHandler.of(JsonObject.class),
                null
            ).body();
            checkAndThrow(validationData);

            return String.join(" ", Rson.DEFAULT.fromJson(validationData.get("scopes"), String[].class));
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    private static void checkAndThrow(JsonObject body) throws ApiAuthException {
        if (body.containsKey("error") || body.containsKey("errors")) {
            throw new ApiAuthException(body.toString());
        }
    }

}
