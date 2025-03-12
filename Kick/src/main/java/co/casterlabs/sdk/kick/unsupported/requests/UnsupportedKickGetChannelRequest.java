package co.casterlabs.sdk.kick.unsupported.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickApi;
import co.casterlabs.sdk.kick.unsupported.types.UnsupportedKickChannel;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class UnsupportedKickGetChannelRequest extends WebRequest<UnsupportedKickChannel> {
    private @Setter String slug;

    @Override
    protected UnsupportedKickChannel execute() throws ApiException, ApiAuthException, IOException {
        assert this.slug != null : "You must specify a slug to query for.";

        return WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(UnsupportedKickApi.API_BASE_URL + "/api/v1/channels/" + this.slug)),
            RsonBodyHandler.of(UnsupportedKickChannel.class),
            null
        ).body();
    }

}
