package co.casterlabs.sdk.tiktok;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider.OAuthResponse;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import okhttp3.FormBody;
import okhttp3.Request;

public class TiktokOAuthStrategy implements OAuthStrategy {

    @Override
    public OAuthResponse codeGrant(String _1, String code, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
        String url = "https://open.tiktokapis.com/v2/oauth/token/";

        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "authorization_code");
        body.put("code", code);
        body.put("client_key", clientId);
        body.put("client_secret", clientSecret);
        body.put("redirect_uri", redirectUri);

        try {
            JsonObject json = sendAuthHttp(body, url);
            checkAndThrow(json);

            return Rson.DEFAULT.fromJson(
                json,
                OAuthResponse.class
            );
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    @Override
    public OAuthResponse refresh(String _1, String refreshToken, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
        String url = "https://open.tiktokapis.com/v2/oauth/token/";

        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "refresh_token");
        body.put("refresh_token", refreshToken);
        body.put("client_key", clientId);
        body.put("client_secret", clientSecret);

        try {
            JsonObject json = sendAuthHttp(body, url);
            checkAndThrow(json);

            OAuthResponse data = Rson.DEFAULT.fromJson(
                json,
                OAuthResponse.class
            );

            return data;
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    protected static JsonObject sendAuthHttp(Map<String, String> body, String url) throws IOException {
        FormBody.Builder form = new FormBody.Builder();
        for (Entry<String, String> entry : body.entrySet()) {
            form.addEncoded(entry.getKey(), entry.getValue());
        }

        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(url)
                .post(form.build()),
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
