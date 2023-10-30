package co.casterlabs.sdk.tiktok;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider.OAuthResponse;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class TiktokOAuthStrategy implements OAuthStrategy {

    @Override
    public OAuthResponse codeGrant(String _1, String code, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
        String url = String.format(
            "https://open.tiktokapis.com/v2/oauth/token?grant_type=authorization_code&code=%s&client_key=%s&client_secret=%s",
            code, clientId, clientSecret
        );

        try {
            JsonObject json = sendAuthHttp(new JsonObject(), url);
            checkAndThrow(json);

            return Rson.DEFAULT.fromJson(
                json.getObject("data"),
                OAuthResponse.class
            );
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    @Override
    public OAuthResponse refresh(String _1, String refreshToken, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
        String url = String.format(
            "https://open.tiktokapis.com/v2/oauth/token?grant_type=refresh_token&refresh_token=%s&client_key=%s&client_secret=%s",
            refreshToken, clientId, clientSecret
        );

        try {
            JsonObject json = sendAuthHttp(new JsonObject(), url);
            checkAndThrow(json);

            OAuthResponse data = Rson.DEFAULT.fromJson(
                json.getObject("data"),
                OAuthResponse.class
            );

            return data;
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    protected static JsonObject sendAuthHttp(JsonObject body, String url) throws IOException {
        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(url)
                .post(
                    RequestBody.create(
                        body.toString(),
                        MediaType.get("application/json")
                    )
                ),
            null
        );

        return Rson.DEFAULT.fromJson(response, JsonObject.class);
    }

    protected static void checkAndThrow(JsonObject body) throws ApiAuthException {
        if (body.containsKey("error_code")) {
            int code = body.getNumber("error_code").intValue();

            if (code != 0) {
                throw new ApiAuthException(body.toString());
            }
        }
    }

}
