package co.casterlabs.sdk.dlive;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Base64;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider.OAuthResponse;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;

@SuppressWarnings("deprecation")
class DliveOAuthStrategy implements OAuthStrategy {

    @Override
    public OAuthResponse codeGrant(String endpoint, String code, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
        String url = String.format(
            "%s?grant_type=authorization_code&code=%s&redirect_uri=%s",
            endpoint, code, URLEncoder.encode(redirectUri)
        );

        try {
            JsonObject json = sendAuthHttp(new JsonObject(), url, clientId, clientSecret);
            checkAndThrow(json);

            return Rson.DEFAULT.fromJson(json, OAuthResponse.class);
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    @Override
    public OAuthResponse refresh(String endpoint, String refreshToken, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
        String url = String.format(
            "%s?grant_type=refresh_token&refresh_token=%s&redirect_uri=%s",
            endpoint, refreshToken, URLEncoder.encode(redirectUri)
        );

        try {
            JsonObject json = sendAuthHttp(new JsonObject(), url, clientId, clientSecret);
            checkAndThrow(json);

            OAuthResponse data = Rson.DEFAULT.fromJson(json, OAuthResponse.class);

            return data;
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    static JsonObject sendAuthHttp(JsonObject body, String url, String clientId, String clientSecret) throws IOException {
        String credential = "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

        return WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", credential)
                .POST(BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json"),
            RsonBodyHandler.of(JsonObject.class),
            null
        ).body();
    }

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
