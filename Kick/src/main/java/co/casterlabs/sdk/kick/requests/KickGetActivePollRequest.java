package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.KickApi;
import co.casterlabs.sdk.kick.types.KickPoll;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class KickGetActivePollRequest extends WebRequest<KickPoll> {
    private @Setter String channelSlug;

    @Override
    protected @Nullable KickPoll execute() throws ApiException, IOException {
        assert this.channelSlug != null : "You must specify a channel slug.";

        JsonObject json = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(KickApi.API_BASE_URL + "/api/v2/channels/" + this.channelSlug + "/polls")),
            RsonBodyHandler.of(JsonObject.class),
            null
        ).body();

        if (json.getObject("status").getBoolean("error")) {
            String message = json.getObject("status").getString("message");

            if (message.equalsIgnoreCase("Poll not found")) {
                return null;
            } else {
                throw new ApiException(message);
            }
        }

        return Rson.DEFAULT.fromJson(
            json
                .getObject("data")
                .get("poll"),
            KickPoll.class
        );
    }

}
