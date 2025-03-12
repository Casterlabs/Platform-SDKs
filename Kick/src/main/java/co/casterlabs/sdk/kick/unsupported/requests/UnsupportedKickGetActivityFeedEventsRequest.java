package co.casterlabs.sdk.kick.unsupported.requests;

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
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickApi;
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickAuth;
import co.casterlabs.sdk.kick.unsupported.types.UnsupportedKickActivityFeedEvent;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class UnsupportedKickGetActivityFeedEventsRequest extends AuthenticatedWebRequest<List<UnsupportedKickActivityFeedEvent>, UnsupportedKickAuth> {
    private @Setter String channelSlug;

    public UnsupportedKickGetActivityFeedEventsRequest(@NonNull UnsupportedKickAuth auth) {
        super(auth);
    }

    @Override
    protected List<UnsupportedKickActivityFeedEvent> execute() throws ApiException, ApiAuthException, IOException {
        assert this.channelSlug != null : "You must specify a channel slug.";

        JsonObject json = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(UnsupportedKickApi.API_BASE_URL + "/api/internal/v1/channels/" + this.channelSlug + "/events"))
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
            new TypeToken<List<UnsupportedKickActivityFeedEvent>>() {
            }
        );
    }

}
