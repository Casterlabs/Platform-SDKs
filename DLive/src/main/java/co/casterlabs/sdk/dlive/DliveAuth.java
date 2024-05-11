package co.casterlabs.sdk.dlive;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Base64;

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
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;

public class DliveAuth extends AuthProvider<DliveAuthData> {
    private final Object lock = new Object();

    private String clientId;
    private String clientSecret;
    private @Nullable String redirectUri; // Null when application auth.
    private boolean isApplicationAuth = false;

    /* ---------------- */
    /* Construction     */
    /* ---------------- */

    private DliveAuth(AuthDataProvider<DliveAuthData> dataProvider) {
        super(dataProvider);
    }

    public static DliveAuth ofUser(AuthDataProvider<DliveAuthData> dataProvider, String clientId, String clientSecret, String redirectUri) {
        DliveAuth auth = new DliveAuth(dataProvider);
        auth.clientId = clientId;
        auth.clientSecret = clientSecret;
        auth.redirectUri = redirectUri;
        return auth;
    }

    public static DliveAuth ofApplication(String clientId, String clientSecret) {
        DliveAuth auth = new DliveAuth(new InMemoryAuthDataProvider<>(DliveAuthData.of(null)));
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
        request.header("Authorization", this.data().accessToken);
    }

    @Override
    public void refresh() throws ApiAuthException {
        synchronized (this.lock) {
            try {
                String credential = "Basic " + Base64.getEncoder().encodeToString((this.clientId + ":" + this.clientSecret).getBytes());
                String body;

                if (this.isApplicationAuth) {
                    body = "grant_type=client_credentials";
                } else {
                    body = String.format(
                        "grant_type=refresh_token&refresh_token=%s&redirect_uri=%s",
                        UriEscape.escapeUriQueryParam(this.data().refreshToken),
                        UriEscape.escapeUriQueryParam(this.redirectUri)
                    );
                }

                JsonObject json = WebRequest.sendHttpRequest(
                    HttpRequest.newBuilder()
                        .uri(URI.create("https://dlive.tv/o/token"))
                        .header("Authorization", credential)
                        .POST(BodyPublishers.ofString(body))
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
        if (body.containsKey("error")) {
            String error = body.getString("error");
            String description;

            if (body.containsKey("error_description")) {
                description = body.getString("error_description");
            } else if (body.containsKey("message")) {
                description = body.getString("message");
            } else {
                description = body.toString();
            }

            throw new ApiAuthException(error + ": " + description);
        } else if (body.containsKey("errors")) {
            throw new ApiAuthException(body.get("errors").toString());
        }
    }

}
