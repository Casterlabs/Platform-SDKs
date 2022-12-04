package co.casterlabs.trovoapi;

import java.io.IOException;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider.OAuthResponse;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

// Because it would be too hard to properly implement OAuth...
class TrovoOAuthStrategy implements OAuthStrategy {
    private static final String VALIDATE_URL = "https://open-api.trovo.live/openplatform/validate";

    @Override
    public OAuthResponse codeGrant(String endpoint, String code, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
        try {
            JsonObject payload = new JsonObject()
                .put("client_secret", clientSecret)
                .put("grant_type", "authorization_code")
                .put("code", code)
                .put("redirect_uri", redirectUri);

            JsonObject json = sendAuthHttp(payload, endpoint, clientId);
            checkAndThrow(json);

            return Rson.DEFAULT.fromJson(json, OAuthResponse.class);
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    @Override
    public OAuthResponse refresh(String endpoint, String refreshToken, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
        try {
            JsonObject payload = new JsonObject()
                .put("client_secret", clientSecret)
                .put("grant_type", "refresh_token")
                .put("refresh_token", refreshToken);

            JsonObject json = sendAuthHttp(payload, endpoint, clientId);
            checkAndThrow(json);

            // We don't deserialize because we only need the access token.
            // We intend on replacing the scope field with our own data from the validation
            // endpoint.
            String accessToken = json.getString("access_token");

            AuthValidationData validationData = validate(clientId, accessToken);
            String scope = String.join(" ", validationData.scopes);

            // Replace.
            json.put("scope", scope);

            // Now we deserialize the response.
            return Rson.DEFAULT.fromJson(json, OAuthResponse.class);
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    private static AuthValidationData validate(String clientId, String accessToken) throws ApiAuthException, IOException {
        JsonObject json = sendAuthHttp(VALIDATE_URL, clientId, accessToken);
        checkAndThrow(json);
        return Rson.DEFAULT.fromJson(json, AuthValidationData.class);
    }

    private static JsonObject sendAuthHttp(JsonObject body, String url, String clientId) throws IOException {
        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(url)
                .header("Client-ID", clientId)
                .header("Accept", "application/json")
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

    private static JsonObject sendAuthHttp(String url, String clientId, String accessToken) throws IOException {
        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(url)
                .header("Client-ID", clientId)
                .header("Authorization", "OAuth " + accessToken)
                .header("Accept", "application/json")
                .get(),
            null
        );

        return Rson.DEFAULT.fromJson(response, JsonObject.class);
    }

    private static void checkAndThrow(JsonObject body) throws ApiAuthException {
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

    @JsonClass(exposeAll = true)
    private static class AuthValidationData {
        @JsonField("uid")
        private String userId;

        @JsonField("client_ID")
        private String clientID;

        @JsonField("nick_name")
        private String nickname;

        @JsonField("expire_ts")
        private String expireTimestamp;

        @JsonField("scopes")
        private List<String> scopes;

        @SuppressWarnings("unused")
        public AuthValidationData() {}

    }

}
