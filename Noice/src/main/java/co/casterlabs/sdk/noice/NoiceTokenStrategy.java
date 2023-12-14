package co.casterlabs.sdk.noice;

import java.io.IOException;
import java.time.Instant;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider.OAuthResponse;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class NoiceTokenStrategy extends OAuthStrategy.Default {
    public static final OkHttpClient client = new OkHttpClient();

    @Override
    public OAuthResponse codeGrant(String endpoint, String code, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
        return null;
    }

    @Override
    public OAuthResponse refresh(String endpoint, String refreshToken, String __redirectUri, String __clientId, String __clientSecret) throws ApiAuthException {
        try (Response response = client.newCall(
            new Request.Builder()
                .url(endpoint)
                .header("Cookie", "refresh-token=" + refreshToken)
                .post(RequestBody.create("{}", MediaType.get("text/plain")))
                .build()
        ).execute()) {
            JsonObject responseJson = Rson.DEFAULT.fromJson(response.body().string(), JsonObject.class);
            if (responseJson.containsKey("error")) {
                throw new ApiAuthException(responseJson.getString("error"));
            }

            JsonObject authData = responseJson.getObject("auth");

            String newRefreshToken;
            {
                String cookie = response.headers().get("Set-Cookie");
                newRefreshToken = cookie.substring(cookie.indexOf('=') + 1, cookie.indexOf(';'));
            }

            int expiresIn = (int) (System.currentTimeMillis() - Instant.parse(authData.getString("expiresAt")).toEpochMilli());

            // We need to transform the Noice data into something the OAuth provider can
            // swallow.

            JsonObject formatted = new JsonObject()
                .put("access_token", authData.getString("token"))
                .put("expires_in", expiresIn)
                .put("refresh_token", newRefreshToken)
                .put("scope", "LEGACY")
                .put("token_type", "access_token");

            return Rson.DEFAULT.fromJson(formatted, OAuthResponse.class);
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    private static final void fix(JsonObject data) {}

}
