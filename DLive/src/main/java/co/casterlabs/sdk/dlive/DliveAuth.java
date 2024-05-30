package co.casterlabs.sdk.dlive;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;
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
import co.casterlabs.sdk.dlive.DliveAuth.DliveAuthData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;

@SuppressWarnings("deprecation")
public class DliveAuth extends AuthProvider<DliveAuthData> {
    private final Object lock = new Object();

    private @Getter String clientId;
    private String clientSecret;
    private @Nullable String redirectUri; // Null when application auth.
    private boolean isApplicationAuth = false;

    /* ---------------- */
    /* Construction     */
    /* ---------------- */

    /**
     * User
     */
    protected DliveAuth(AuthDataProvider<DliveAuthData> dataProvider, String clientId, String clientSecret, String redirectUri) {
        super(dataProvider);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    /**
     * Application
     */
    protected DliveAuth(String clientId, String clientSecret) {
        super(new InMemoryAuthDataProvider<>(DliveAuthData.of(null)));
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.isApplicationAuth = true;
    }

    public static DliveAuth ofUser(AuthDataProvider<DliveAuthData> dataProvider, String clientId, String clientSecret, String redirectUri) {
        return new DliveAuth(dataProvider, clientId, clientSecret, redirectUri);
    }

    public static DliveAuth ofApplication(String clientId, String clientSecret) {
        return new DliveAuth(clientId, clientSecret);
    }

    /* ---------------- */
    /* Impl.            */
    /* ---------------- */

    @SneakyThrows
    @Override
    protected void authenticateRequest0(@NonNull HttpRequest.Builder request) {
        request.header("Authorization", this.data().accessToken);
    }

    @Override
    public void refresh() throws ApiAuthException {
        synchronized (this.lock) {
            try {
                String credential = "Basic " + Base64.getEncoder().encodeToString((this.clientId + ":" + this.clientSecret).getBytes());
                Map<String, String> body;

                if (this.isApplicationAuth) {
                    body = Map.of("grant_type", "client_credentials");
                } else {
                    body = Map.of(
                        "grant_type", "refresh_token",
                        "refresh_token", this.data().refreshToken,
                        "redirect_uri", this.redirectUri
                    );
                }

                JsonObject json = WebRequest.sendHttpRequest(
                    HttpRequest.newBuilder()
                        .uri(URI.create("https://dlive.tv/o/token"))
                        .header("Authorization", credential)
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

                DliveAuthData data = Rson.DEFAULT.fromJson(json, DliveAuthData.class);
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
    public static class DliveAuthData {
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

        public static DliveAuthData of(String refreshToken) {
            DliveAuthData d = new DliveAuthData();
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
