package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.KickAuth;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Accessors(chain = true, fluent = true)
public class KickGetPublicKeyRequest extends AuthenticatedWebRequest<String, KickAuth> {

    public KickGetPublicKeyRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected String execute() throws ApiException, ApiAuthException, IOException {
        String url = "https://api.kick.com/public/v1/public-key";

        return _KickApi.request(
            HttpRequest.newBuilder(URI.create(url)),
            this.auth,
            JsonObject.class
        ).getString("public_key");
    }

}
