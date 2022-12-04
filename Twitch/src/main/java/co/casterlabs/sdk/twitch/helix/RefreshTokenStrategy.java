package co.casterlabs.sdk.twitch.helix;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider.OAuthResponse;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;

@SuppressWarnings("deprecation")
class RefreshTokenStrategy extends OAuthStrategy.Default {

    @Override
    public OAuthResponse codeGrant(String endpoint, String code, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
        String url = String.format(
            "%s?grant_type=authorization_code&code=%s&redirect_uri=%s&client_id=%s&client_secret=%s",
            endpoint, code, URLEncoder.encode(redirectUri), clientId, clientSecret
        );

        try {
            JsonObject json = sendAuthHttp(new JsonObject(), url);
            checkAndThrow(json);

            fix(json);

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

            fix(json);

            OAuthResponse data = Rson.DEFAULT.fromJson(json, OAuthResponse.class);

            return data;
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    private static final void fix(JsonObject data) {
        // Twitch's response payload always sends scope back as an array rather than a
        // string. So we parse it and replace it.
        JsonArray scopesArray = data.getArray("scope");
        List<String> scope = new ArrayList<>(scopesArray.size());

        for (JsonElement e : scopesArray) {
            scope.add(e.getAsString());
        }

        data.put("scope", String.join(" ", scope));
    }

}
