package co.casterlabs.sdk.tiktok;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.stream.Collectors;

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
        String url = "https://open.tiktokapis.com/v2/oauth/token";
        Map<String, String> body = Map.of(
            "grant_type", "authorization_code",
            "code", code,
            "client_key", clientId,
            "client_secret", clientSecret,
            "redirect_uri", redirectUri
        );

        try {
            JsonObject json = sendAuthHttp(body, url);
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
        String url = "https://open.tiktokapis.com/v2/oauth/token";

        Map<String, String> body = Map.of(
            "grant_type", "refresh_token",
            "refresh_token", refreshToken,
            "client_key", clientId,
            "client_secret", clientSecret,
            "redirect_uri", redirectUri
        );

        try {
            JsonObject json = sendAuthHttp(body, url);
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

    @SuppressWarnings("deprecation")
    protected static JsonObject sendAuthHttp(Map<String, String> body, String url) throws IOException {
        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(url)
                .post(
                    RequestBody.create(
                        body
                            .entrySet()
                            .stream()
                            .map((e) -> URLEncoder.encode(e.getKey()) + "=" + URLEncoder.encode(e.getValue()))
                            .collect(Collectors.joining("&")),
                        MediaType.get("application/x-www-form-urlencoded")
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
