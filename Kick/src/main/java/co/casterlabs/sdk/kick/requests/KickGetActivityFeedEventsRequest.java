package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.KickApi;
import co.casterlabs.sdk.kick.KickAuth;
import co.casterlabs.sdk.kick.types.KickActivityFeedEvent;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class KickGetActivityFeedEventsRequest extends AuthenticatedWebRequest<List<KickActivityFeedEvent>, KickAuth> {
    private @Setter String channelSlug;

    public KickGetActivityFeedEventsRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected List<KickActivityFeedEvent> execute() throws ApiException, ApiAuthException, IOException {
        assert this.channelSlug != null : "You must specify a channel slug.";

        JsonObject json = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(KickApi.API_BASE_URL + "/api/internal/v1/channels/" + this.channelSlug + "/events"))
                .header("Accept", "application/json"),
            RsonBodyHandler.of(JsonObject.class),
            this.auth
        ).body();

        if (json.getObject("status").getBoolean("error")) {
            String message = json.getObject("status").getString("message");
            throw new ApiException(message);
        }

        return Rson.DEFAULT.fromJson(
            json
                .getObject("data")
                .get("events"),
            new TypeToken<List<KickActivityFeedEvent>>() {
            }
        );
    }

}
