package co.casterlabs.sdk.dlive;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider.OAuthResponse;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;

class DliveClientCredentialsStrategy implements OAuthStrategy {

    @Override
    public OAuthResponse codeGrant(String endpoint, String code, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
        throw new UnsupportedOperationException();
    }

    @Override
    public OAuthResponse refresh(String endpoint, String _1, String _2, String clientId, String clientSecret) throws ApiAuthException {
        String url = String.format(
            "%s?grant_type=client_credentials",
            endpoint
        );

        try {
            JsonObject json = DliveOAuthStrategy.sendAuthHttp(new JsonObject(), url, clientId, clientSecret);
            DliveOAuthStrategy.checkAndThrow(json);

            // Trick some internal logic into treating this as a normal oauth response.
            json.put("refresh_token", "");

            OAuthResponse data = Rson.DEFAULT.fromJson(json, OAuthResponse.class);

            return data;
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

}
