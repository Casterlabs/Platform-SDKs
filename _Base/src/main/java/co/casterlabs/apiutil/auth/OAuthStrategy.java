package co.casterlabs.apiutil.auth;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.charset.StandardCharsets;

import co.casterlabs.apiutil.auth.OAuthProvider.OAuthResponse;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;

public interface OAuthStrategy {

    public OAuthResponse codeGrant(String endpoint, String code, String redirectUri, String clientId, String clientSecret) throws ApiAuthException;

    public OAuthResponse refresh(String endpoint, String refreshToken, String redirectUri, String clientId, String clientSecret) throws ApiAuthException;

    @SuppressWarnings("deprecation")
    public static class Default implements OAuthStrategy {

        @Override
        public OAuthResponse codeGrant(String endpoint, String code, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
            String url = String.format(
                "%s?grant_type=authorization_code&code=%s&redirect_uri=%s&client_id=%s&client_secret=%s",
                endpoint, code, URLEncoder.encode(redirectUri), clientId, clientSecret
            );

            try {
                JsonObject json = sendAuthHttp(new JsonObject(), url);
                checkAndThrow(json);

                return Rson.DEFAULT.fromJson(json, OAuthResponse.class);
            } catch (IOException e) {
                throw new ApiAuthException(e);
            }
        }

        @Override
        public OAuthResponse refresh(String endpoint, String refreshToken, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
            String url = String.format(
                "%s?grant_type=refresh_token&refresh_token=%s&redirect_uri=%s&client_id=%s&client_secret=%s",
                endpoint, refreshToken, URLEncoder.encode(redirectUri), clientId, clientSecret
            );

            try {
                JsonObject json = sendAuthHttp(new JsonObject(), url);
                checkAndThrow(json);

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
                    .POST(BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8))
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

}
