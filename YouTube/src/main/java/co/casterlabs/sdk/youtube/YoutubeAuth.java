package co.casterlabs.sdk.youtube;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import org.unbescape.uri.UriEscape;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

public class YoutubeAuth extends AuthProvider {
    private @Getter String apiKey;

    private @Getter String clientId;

    private String clientSecret;

    private long lastRefresh = 0;
    private long expiresIn = 0;

    private String refreshToken;
    private String accessToken;

    public YoutubeAuth(@NonNull String apiKey) {
        this.apiKey = apiKey;
    }

    public YoutubeAuth(@NonNull String apiKey, @NonNull String clientId, @NonNull String clientSecret, @NonNull String refreshToken) throws ApiAuthException {
        this.apiKey = apiKey;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;
        this.refresh();
    }

    @Override
    protected void authenticateRequest0(@NonNull HttpRequest.Builder request) {
        if (!this.isApplicationAuth()) {
            request.header("Authorization", "Bearer " + this.accessToken);
        }

        // Inject the client key.
        String url = request.build().uri().toString();
        request.uri(URI.create(url + "&key=" + this.apiKey));
    }

    @Override
    public void refresh() throws ApiAuthException {
        if (!this.isApplicationAuth()) {
            String form = String.format(
                "grant_type=refresh_token"
                    + "&refresh_token=%s"
                    + "&client_id=%s"
                    + "&client_secret=%s",
                UriEscape.escapeUriQueryParam(this.refreshToken),
                UriEscape.escapeUriQueryParam(this.clientId),
                UriEscape.escapeUriQueryParam(this.clientSecret)
            );

            try {
                JsonObject json = YoutubeHttpUtil.insert(form, "application/x-www-form-urlencoded", "https://oauth2.googleapis.com/token", null);
                if (json.containsKey("error")) {
                    throw new ApiAuthException(json.toString());
                } else {
                    AuthResponse data = Rson.DEFAULT.fromJson(json, AuthResponse.class);

                    this.accessToken = data.accessToken;
                    this.expiresIn = data.expiresIn;
                    this.lastRefresh = System.currentTimeMillis();
                }
            } catch (ApiAuthException e) {
                throw e;
            } catch (IOException | ApiException e) {
                throw new ApiAuthException(e);
            }
        }
    }

    @Override
    public boolean isApplicationAuth() {
        return this.refreshToken == null;
    }

    @Override
    public boolean isExpired() {
        if (this.isApplicationAuth()) {
            return false;
        }

        return ((System.currentTimeMillis() - this.lastRefresh) / 1000) > this.expiresIn;
    }

    public static AuthResponse authorize(String code, String redirectUri, String clientId, String clientSecret) throws IOException, ApiAuthException {
        String form = String.format(
            "grant_type=authorization_code"
                + "&code=%s"
                + "&redirect_uri=%s"
                + "&client_id=%s"
                + "&client_secret=%s",
            UriEscape.escapeUriQueryParam(code),
            UriEscape.escapeUriQueryParam(redirectUri),
            UriEscape.escapeUriQueryParam(clientId),
            UriEscape.escapeUriQueryParam(clientSecret)
        );

        try {
            JsonObject json = YoutubeHttpUtil.insert(form, "application/x-www-form-urlencoded", "https://oauth2.googleapis.com/token", null);
            if (json.containsKey("error")) {
                throw new ApiAuthException(json.toString());
            } else {
                return Rson.DEFAULT.fromJson(json, AuthResponse.class);
            }
        } catch (ApiAuthException e) {
            throw e;
        } catch (IOException | ApiException e) {
            throw new ApiAuthException(e);
        }
    }

    public static String generateOAuth2Url(@NonNull String clientId, @NonNull String redirectUri, @NonNull String scope, @NonNull String state) {
        return String.format(
            "https://accounts.google.com/o/oauth2/v2/auth"
                + "?scope=%s"
                + "&state=%s"
                + "&redirect_uri=%s"
                + "&response_type=code"
                + "&prompt=consent"
                + "&access_type=offline"
                + "&client_id=%s",
            UriEscape.escapeUriQueryParam(scope),
            UriEscape.escapeUriQueryParam(state),
            UriEscape.escapeUriQueryParam(redirectUri),
            UriEscape.escapeUriQueryParam(clientId)
        );
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class AuthResponse {
        @JsonField("access_token")
        private String accessToken;

        @JsonField("refresh_token")
        private String refreshToken;

        @JsonField("expires_in")
        private int expiresIn;

        @JsonField("token_type")
        private String tokenType;

    }

}
