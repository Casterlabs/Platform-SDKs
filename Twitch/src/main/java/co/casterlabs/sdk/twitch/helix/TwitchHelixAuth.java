package co.casterlabs.sdk.twitch.helix;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Map;
import java.util.stream.Collectors;

import org.unbescape.uri.UriEscape;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthDataProvider;
import co.casterlabs.apiutil.auth.AuthDataProvider.InMemoryAuthDataProvider;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth.TwitchHelixAuthData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;

public class TwitchHelixAuth extends AuthProvider<TwitchHelixAuthData> {
    private final Object lock = new Object();

    private @Getter String clientId;
    private String clientSecret;
    private boolean isApplicationAuth = false;

    /* ---------------- */
    /* Construction     */
    /* ---------------- */

    private TwitchHelixAuth(AuthDataProvider<TwitchHelixAuthData> dataProvider) {
        super(dataProvider);
    }

    public static TwitchHelixAuth ofUser(AuthDataProvider<TwitchHelixAuthData> dataProvider, String clientId, String clientSecret) {
        TwitchHelixAuth auth = new TwitchHelixAuth(dataProvider);
        auth.clientId = clientId;
        auth.clientSecret = clientSecret;
        return auth;
    }

    public static TwitchHelixAuth ofApplication(String clientId, String clientSecret) {
        TwitchHelixAuth auth = new TwitchHelixAuth(new InMemoryAuthDataProvider<>(TwitchHelixAuthData.of(null)));
        auth.clientId = clientId;
        auth.clientSecret = clientSecret;
        auth.isApplicationAuth = true;
        return auth;
    }

    /* ---------------- */
    /* Impl.            */
    /* ---------------- */

    @SneakyThrows
    @Override
    protected void authenticateRequest0(@NonNull HttpRequest.Builder request) {
        request.header("Authorization", String.join(" ", this.data().tokenType, this.data().accessToken));
        request.header("Client-ID", this.clientId);
    }

    @Override
    public void refresh() throws ApiAuthException {
        synchronized (this.lock) {
            try {
                Map<String, String> body;

                if (this.isApplicationAuth) {
                    body = Map.of(
                        "grant_type", "client_credentials",
                        "client_id", this.clientId,
                        "client_secret", this.clientSecret
                    );
                } else {
                    body = Map.of(
                        "grant_type", "refresh_token",
                        "refresh_token", this.data().refreshToken,
                        "client_id", this.clientId,
                        "client_secret", this.clientSecret
                    );
                }

                JsonObject json = WebRequest.sendHttpRequest(
                    HttpRequest.newBuilder()
                        .uri(URI.create("https://id.twitch.tv/oauth2/token"))
                        .POST(
                            BodyPublishers.ofString(
                                body.entrySet()
                                    .stream()
                                    .map((e) -> UriEscape.escapeUriQueryParam(e.getKey()) + "=" + UriEscape.escapeUriQueryParam(e.getValue()))
                                    .collect(Collectors.joining("&"))
                            )
                        )
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

                TwitchHelixAuthData data = Rson.DEFAULT.fromJson(json, TwitchHelixAuthData.class);
                this.dataProvider.save(data);
            } catch (IOException e) {
                throw new ApiAuthException(e);
            }
        }
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
            return ((System.currentTimeMillis() - this.data().issuedAt) / 1000) > this.data().expiresIn;
        }
    }

    @EqualsAndHashCode
    @NoArgsConstructor
    @JsonClass(exposeAll = true)
    public static class TwitchHelixAuthData {
        @JsonField("issued_at")
        private long issuedAt = System.currentTimeMillis();

        @JsonField("access_token")
        private String accessToken;

        @JsonField("expires_in")
        private int expiresIn;

        @JsonField("refresh_token")
        private String refreshToken;

        private String scope;

        @JsonField("token_type")
        private String tokenType;

        public static TwitchHelixAuthData of(String refreshToken) {
            TwitchHelixAuthData d = new TwitchHelixAuthData();
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
