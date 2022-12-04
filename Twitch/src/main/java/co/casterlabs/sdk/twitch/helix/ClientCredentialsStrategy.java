package co.casterlabs.sdk.twitch.helix;

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
