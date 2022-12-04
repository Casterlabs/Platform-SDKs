package co.casterlabs.youtubeapijava;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import okhttp3.Request.Builder;
import okhttp3.Response;

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
    protected void authenticateRequest0(@NonNull Builder request) {
        if (!this.isApplicationAuth()) {
            request.addHeader("Authorization", "Bearer " + this.accessToken);
        }

        // Inject the client key.
        String url = request.getUrl$okhttp().toString();

        request.url(url + "&key=" + this.apiKey);
    }

    @Override
    public void refresh() throws ApiAuthException {
        if (!this.isApplicationAuth()) {
            String form = String.format(
                "grant_type=refresh_token"
                    + "&refresh_token=%s"
                    + "&client_id=%s"
                    + "&client_secret=%s",
                HttpUtil.encodeURIComponent(this.refreshToken),
                HttpUtil.encodeURIComponent(this.clientId),
                HttpUtil.encodeURIComponent(this.clientSecret)
            );

            try (Response response = HttpUtil.sendHttp(form, "https://oauth2.googleapis.com/token", null, "application/x-www-form-urlencoded")) {
                String body = response.body().string();

                JsonObject json = YoutubeApi.RSON.fromJson(body, JsonObject.class);

                if (json.containsKey("error")) {
                    throw new ApiAuthException(body);
                } else {
                    AuthResponse data = YoutubeApi.RSON.fromJson(json, AuthResponse.class);

                    this.accessToken = data.accessToken;
                    this.expiresIn = data.expiresIn;
                    this.lastRefresh = System.currentTimeMillis();
                }
            } catch (IOException e) {
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
            HttpUtil.encodeURIComponent(code),
            HttpUtil.encodeURIComponent(redirectUri),
            HttpUtil.encodeURIComponent(clientId),
            HttpUtil.encodeURIComponent(clientSecret)
        );

        try (Response response = HttpUtil.sendHttp(form, "https://oauth2.googleapis.com/token", null, "application/x-www-form-urlencoded")) {
            String body = response.body().string();

            JsonObject json = YoutubeApi.RSON.fromJson(body, JsonObject.class);

            if (json.containsKey("error")) {
                throw new ApiAuthException(body);
            } else {
                return YoutubeApi.RSON.fromJson(json, AuthResponse.class);
            }
        } catch (JsonParseException e) {
            throw new IOException(e); // :^)
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
            HttpUtil.encodeURIComponent(scope),
            HttpUtil.encodeURIComponent(state),
            HttpUtil.encodeURIComponent(redirectUri),
            HttpUtil.encodeURIComponent(clientId)
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
