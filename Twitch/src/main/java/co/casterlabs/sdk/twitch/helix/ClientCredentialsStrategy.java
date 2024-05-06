package co.casterlabs.sdk.twitch.helix;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider.OAuthResponse;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;

class ClientCredentialsStrategy implements OAuthStrategy {

    @Override
    public OAuthResponse codeGrant(String endpoint, String code, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
        return null; // Unused.
    }

    @Override
    public OAuthResponse refresh(String endpoint, String refreshToken, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
        String url = String.format(
            "%s?grant_type=client_credentials&client_id=%s&client_secret=%s",
            endpoint, clientId, clientSecret
        );

        try {
            JsonObject json = sendAuthHttp(new JsonObject(), url);
            checkAndThrow(json);

            // We need this field for ApiUtil.
            json.put("refresh_token", "CLIENT_CREDENTIALS");

            OAuthResponse data = Rson.DEFAULT.fromJson(json, OAuthResponse.class);

            return data;
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    protected static JsonObject sendAuthHttp(JsonObject body, String url) throws IOException {
        return WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json"),
            RsonBodyHandler.of(JsonObject.class),
            null
        ).body();
    }

    protected static void checkAndThrow(JsonObject body) throws ApiAuthException {
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
