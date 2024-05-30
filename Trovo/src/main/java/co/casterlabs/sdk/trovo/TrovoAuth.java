package co.casterlabs.sdk.trovo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthDataProvider;
import co.casterlabs.apiutil.auth.AuthDataProvider.InMemoryAuthDataProvider;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.apiutil.web.ApiException;
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
    private final Object lock = new Object();

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

        synchronized (this.lock) {
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

                // We don't deserialize because we only need the access token.
                // We intend on replacing the scope field with our own data from the validation
                // endpoint.
                JsonObject validationData = WebRequest.sendHttpRequest(
                    HttpRequest.newBuilder()
                        .uri(URI.create("https://open-api.trovo.live/openplatform/validate"))
                        .header("Authorization", "OAuth " + authData.getString("access_token"))
                        .header("Client-ID", this.clientId)
                        .header("Accept", "application/json"),
                    RsonBodyHandler.of(JsonObject.class),
                    null
                ).body();
                checkAndThrow(validationData);

                // Replace.
                String scope = String.join(" ", Rson.DEFAULT.fromJson(validationData.get("scopes"), String[].class));
                authData.put("scope", scope);

                TrovoAuthData data = Rson.DEFAULT.fromJson(authData, TrovoAuthData.class);
                this.dataProvider.save(data);
            } catch (IOException e) {
                throw new ApiAuthException(e);
            }
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
        synchronized (this.lock) {
            if (this.isExpired()) {
                this.refresh();
            }
            return this.data().accessToken;
        }
    }

    @Override
    public boolean isApplicationAuth() {
        return this.isApplicationAuth;
    }

    @Override
    public boolean isExpired() {
        synchronized (this.lock) {
            TrovoAuthData data = this.data();

            if (data.accessToken == null) {
                return true;
            }

            long secondsSinceIssuance = (System.currentTimeMillis() - data.issuedAt) / 1000;
            return secondsSinceIssuance > data.expiresIn;
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
    /* Utils            */
    /* ---------------- */

    static void checkAndThrow(JsonObject body) throws ApiAuthException {
        if (body.containsKey("error") || body.containsKey("errors")) {
            throw new ApiAuthException(body.toString());
        }
    }

}
