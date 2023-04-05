package co.casterlabs.sdk.kick.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.sdk.kick.KickApi;
import co.casterlabs.sdk.kick.types.KickChannel;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Request;

@Accessors(chain = true)
public class KickGetChannelRequest extends WebRequest<KickChannel> {
    private @Setter String slug;

    @Override
    protected KickChannel execute() throws ApiException, ApiAuthException, IOException {
        assert this.slug != null : "You must specify a slug to query for.";

        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(KickApi.API_BASE_URL + "/api/v1/channels/" + this.slug),
            null
        );

        return Rson.DEFAULT.fromJson(response, KickChannel.class);
    }

}
