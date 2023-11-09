package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
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
import okhttp3.Request;

@Accessors(chain = true)
public class KickGetActivityFeedEventsRequest extends AuthenticatedWebRequest<List<KickActivityFeedEvent>, KickAuth> {
    private @Setter String channelSlug;

    public KickGetActivityFeedEventsRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected List<KickActivityFeedEvent> execute() throws ApiException, ApiAuthException, IOException {
        assert this.channelSlug != null : "You must specify a channel slug.";

        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(KickApi.API_BASE_URL + "/api/internal/v1/channels/" + this.channelSlug + "/events")
                .header("Accept", "application/json"),
            this.auth
        );
        JsonObject json = Rson.DEFAULT.fromJson(response, JsonObject.class);

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
