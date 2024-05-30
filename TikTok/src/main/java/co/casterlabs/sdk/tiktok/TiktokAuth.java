package co.casterlabs.sdk.tiktok;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Map;
import java.util.stream.Collectors;

import org.unbescape.uri.UriEscape;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthDataProvider;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.tiktok.TiktokAuth.TiktokAuthData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;

@SuppressWarnings("deprecation")
public class TiktokAuth extends AuthProvider<TiktokAuthData> {
    private final Object lock = new Object();

    private @Getter String clientId;
    private String clientSecret;

    /* ---------------- */
    /* Construction     */
    /* ---------------- */

    /**
     * User
     */
    protected TiktokAuth(AuthDataProvider<TiktokAuthData> dataProvider, String clientId, String clientSecret) {
        super(dataProvider);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public static TiktokAuth ofUser(AuthDataProvider<TiktokAuthData> dataProvider, String clientId, String clientSecret) {
        return new TiktokAuth(dataProvider, clientId, clientSecret);
    }

    /* ---------------- */
    /* Impl.            */
    /* ---------------- */

    @SneakyThrows
    @Override
    protected void authenticateRequest0(@NonNull HttpRequest.Builder request) {
        request.header("Authorization", "Bearer " + this.data().accessToken);
    }

    @Override
    public void refresh() throws ApiAuthException {
        synchronized (this.lock) {
            try {
                Map<String, String> body = Map.of(
                    "grant_type", "refresh_token",
                    "refresh_token", this.data().refreshToken,
                    "client_key", this.clientId,
                    "client_secret", this.clientSecret
                );

                JsonObject json = WebRequest.sendHttpRequest(
                    HttpRequest.newBuilder()
                        .uri(URI.create("https://open.tiktokapis.com/v2/oauth/token/"))
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

                TiktokAuthData data = Rson.DEFAULT.fromJson(json, TiktokAuthData.class);
                this.dataProvider.save(data);
            } catch (IOException e) {
                throw new ApiAuthException(e);
            }
        }
    }

    @Override
    public boolean isApplicationAuth() {
        return false;
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

        public static TiktokAuthData of(String refreshToken) {
            TiktokAuthData d = new TiktokAuthData();
            d.issuedAt = 0;
            d.refreshToken = refreshToken;
            return d;
        }

    }

    /* ---------------- */
    /* Utils            */
    /* ---------------- */

    static void checkAndThrow(JsonObject body) throws ApiAuthException {
        if (body.containsKey("error_code")) {
            int code = body.getNumber("error_code").intValue();

            if (code != 0) {
                throw new ApiAuthException(body.toString());
            }
        }
    }

}
