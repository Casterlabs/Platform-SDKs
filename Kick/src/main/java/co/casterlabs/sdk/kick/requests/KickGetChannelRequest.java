package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.kick.KickApi;
import co.casterlabs.sdk.kick.types.KickChannel;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class KickGetChannelRequest extends WebRequest<KickChannel> {
    private @Setter String slug;

    @Override
    protected KickChannel execute() throws ApiException, ApiAuthException, IOException {
        assert this.slug != null : "You must specify a slug to query for.";

        return WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(KickApi.API_BASE_URL + "/api/v1/channels/" + this.slug)),
            RsonBodyHandler.of(KickChannel.class),
            null
        ).body();
    }

}
