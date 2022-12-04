package co.casterlabs.zohoapijava;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.Getter;
import lombok.NonNull;
import okhttp3.Request.Builder;
import okhttp3.Response;

@Getter
public class ZohoAuth extends AuthProvider {
    private String refreshToken;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String scope;

    private long expiresAt;

    private String accessToken;

    public ZohoAuth(@NonNull String refreshToken, @NonNull String clientId, @NonNull String clientSecret, @NonNull String redirectUri, @NonNull String scope) throws ApiAuthException {
        this.refreshToken = refreshToken;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.scope = scope;

        this.refresh();
    }

    @Override
    public void refresh() throws ApiAuthException {
        String url = String.format(
            "https://accounts.zoho.com/oauth/v2/token" +
                "?refresh_token=%s" +
                "&client_id=%s" +
                "&client_secret=%s" +
                "&redirect_uri=%s" +
                "&scope=%s" +
                "&grant_type=refresh_token",
            this.refreshToken,
            this.clientId,
            this.clientSecret,
            this.redirectUri,
            this.scope
        );

        try (Response response = ZohoHttpUtil.sendHttp("{}", url, null, null, null)) {
            JsonObject json = Rson.DEFAULT.fromJson(response.body().string(), JsonObject.class);

            if (json.containsKey("error")) {
                throw new ApiAuthException(json.getString("error"));
            } else {
                long expiresIn = json.getNumber("expires_in").longValue() * 1000; // Secs -> Millis

                this.accessToken = json.getString("access_token");
                this.expiresAt = System.currentTimeMillis() + expiresIn;
            }
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    @Override
    protected void authenticateRequest0(@NonNull Builder request) {
        request.header("Authorization", "Zoho-oauthtoken " + this.accessToken);

    }

    @Override
    public boolean isApplicationAuth() {
        return false;
    }

    @Override
    public boolean isExpired() {
        return System.currentTimeMillis() > this.expiresAt;
    }

    public static ZohoAuth verifyOAuthCode(@NonNull String code, @NonNull String clientId, @NonNull String clientSecret, @NonNull String redirectUri, @NonNull String scope) throws ApiAuthException {
        String url = String.format(
            "https://accounts.zoho.com/oauth/v2/token" +
                "?code=%s" +
                "&client_id=%s" +
                "&client_secret=%s" +
                "&redirect_uri=%s" +
                "&scope=%s" +
                "&grant_type=authorization_code",
            code,
            clientId,
            clientSecret,
            redirectUri,
            scope
        );

        try (Response response = ZohoHttpUtil.sendHttp("{}", url, null, null, null)) {
            JsonObject json = Rson.DEFAULT.fromJson(response.body().string(), JsonObject.class);

            if (json.containsKey("error")) {
                throw new ApiAuthException(json.getString("error"));
            } else {
                String refreshToken = json.getString("refresh_token");

                return new ZohoAuth(refreshToken, clientId, clientSecret, redirectUri, scope);
            }
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    public static String getOAuthOfflineUrl(@NonNull String scope, @NonNull String clientId, @NonNull String redirectUri) {
        return String.format(
            "https://accounts.zoho.com/oauth/v2/auth" +
                "?scope=%s&client_id=%s" +
                "&response_type=code" +
                "&access_type=offline" +
                "&redirect_uri=%s",
            scope,
            clientId,
            redirectUri
        );
    }

}
