package co.casterlabs.brimeapijava;

import java.io.IOException;
import java.net.URLEncoder;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider.OAuthResponse;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

// Auth0 doesn't accept anything except www-form-urlencoded. So we must oblige.
@SuppressWarnings("deprecation")
class BrimeOAuthStrategy implements OAuthStrategy {

    @Override
    public OAuthResponse codeGrant(String endpoint, String code, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
        String form = String.format(
            "grant_type=authorization_code"
                + "&code=%s"
                + "&redirect_uri=%s"
                + "&client_id=%s"
                + "&client_secret=%s",
            URLEncoder.encode(code),
            URLEncoder.encode(redirectUri),
            URLEncoder.encode(clientId),
            URLEncoder.encode(clientSecret)
        );

        try {
            JsonObject json = sendAuthHttp(form, endpoint);
            checkAndThrow(json);

            return Rson.DEFAULT.fromJson(json, OAuthResponse.class);
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    @Override
    public OAuthResponse refresh(String endpoint, String refreshToken, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
        String form = String.format(
            "grant_type=refresh_token"
                + "&refresh_token=%s"
                + "&client_id=%s"
                + "&client_secret=%s",
            URLEncoder.encode(refreshToken),
            URLEncoder.encode(clientId),
            URLEncoder.encode(clientSecret)
        );

        try {
            JsonObject json = sendAuthHttp(form, endpoint);
            checkAndThrow(json);

            // Auth0 does not grant a new refresh token.
            // Though, most sites just return the current token as if nothing happened, oh
            // well.
            if (!json.containsKey("refresh_token")) {
                json.put("refresh_token", refreshToken);
            }

            OAuthResponse data = Rson.DEFAULT.fromJson(json, OAuthResponse.class);

            return data;
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    protected static JsonObject sendAuthHttp(String formBody, String url) throws IOException {
        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(url)
                .post(
                    RequestBody.create(
                        formBody,
                        MediaType.get("application/x-www-form-urlencoded")
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
