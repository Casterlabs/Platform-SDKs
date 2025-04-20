package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.experimental.Accessors;

@Accessors(chain = true, fluent = true)
public class KickGetPublicKeyRequest extends WebRequest<String> {

    @Override
    protected String execute() throws ApiException, IOException {
        String url = "https://api.kick.com/public/v1/public-key";

        return _KickApi.request(
            HttpRequest.newBuilder(URI.create(url)),
            null,
            JsonObject.class
        ).getString("public_key");
    }

}
