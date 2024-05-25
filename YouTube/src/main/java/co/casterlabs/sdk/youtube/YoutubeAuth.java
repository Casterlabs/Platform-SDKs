package co.casterlabs.sdk.youtube;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Map;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;
import org.unbescape.uri.UriEscape;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthDataProvider;
import co.casterlabs.apiutil.auth.AuthDataProvider.InMemoryAuthDataProvider;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.youtube.YoutubeAuth.YoutubeAuthData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

public class YoutubeAuth extends AuthProvider<YoutubeAuthData> {
    private final Object lock = new Object();

    private @Getter String apiKey;
    private @Getter @Nullable String clientId; // Null when application auth.
    private @Nullable String clientSecret; // Null when application auth.
    private boolean isApplicationAuth = false;

    /* ---------------- */
    /* Construction     */
    /* ---------------- */

    private YoutubeAuth(AuthDataProvider<YoutubeAuthData> dataProvider) {
        super(dataProvider);
    }

    public static YoutubeAuth ofUser(AuthDataProvider<YoutubeAuthData> dataProvider, String apiKey, String clientId, String clientSecret) {
        YoutubeAuth auth = new YoutubeAuth(dataProvider);
        auth.apiKey = apiKey;
        auth.clientId = clientId;
        auth.clientSecret = clientSecret;
        return auth;
    }

    public static YoutubeAuth ofApplication(String apiKey) {
        YoutubeAuth auth = new YoutubeAuth(new InMemoryAuthDataProvider<>(YoutubeAuthData.of(null)));
        auth.apiKey = apiKey;
        auth.isApplicationAuth = true;
        return auth;
    }

    /* ---------------- */
    /* Impl.            */
    /* ---------------- */

    @Override
    protected void authenticateRequest0(@NonNull HttpRequest.Builder request) {
        if (!this.isApplicationAuth()) {
            request.header("Authorization", "Bearer " + this.data().accessToken);
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

        synchronized (this.lock) {
            try {
                Map<String, String> body = Map.of(
                    "grant_type", "refresh_token",
                    "refresh_token", this.data().refreshToken,
                    "client_id", this.clientId,
                    "client_secret", this.clientSecret
                );

                JsonObject json = YoutubeHttpUtil.insert(
                    body.entrySet()
                        .stream()
                        .map((e) -> UriEscape.escapeUriQueryParam(e.getKey()) + "=" + UriEscape.escapeUriQueryParam(e.getValue()))
                        .collect(Collectors.joining("&")),
                    "application/x-www-form-urlencoded",
                    "https://oauth2.googleapis.com/token",
                    null
                );
                checkAndThrow(json);

                YoutubeAuthData data = Rson.DEFAULT.fromJson(json, YoutubeAuthData.class);
                this.dataProvider.save(data);
            } catch (ApiAuthException e) {
                throw e;
            } catch (IOException | ApiException e) {
                throw new ApiAuthException(e);
            }
        }

    }

    public String getAccessToken() throws ApiAuthException {
        if (this.isApplicationAuth) return null;
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
        if (this.isApplicationAuth) return false;
        synchronized (this.lock) {
            return ((System.currentTimeMillis() - this.data().issuedAt) / 1000) > this.data().expiresIn;
        }
    }

    @EqualsAndHashCode
    @NoArgsConstructor
    @JsonClass(exposeAll = true)
    public static class YoutubeAuthData {
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

        public static YoutubeAuthData of(String refreshToken) {
            YoutubeAuthData d = new YoutubeAuthData();
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
