package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import lombok.NonNull;
import lombok.Setter;

@Setter
public class TwitchHelixGetStreamKeyRequest extends AuthenticatedWebRequest<String, TwitchHelixAuth> {
    private @NonNull String forBroadcasterId;

    public TwitchHelixGetStreamKeyRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    @Override
    protected String execute() throws ApiException, ApiAuthException, IOException {
        assert this.forBroadcasterId != null : "You must specify the broadcaster ID corresponding to the user you want to retrive a stream key for (must match auth)";

        String url = "https://api.twitch.tv/helix/streams/key?broadcaster_id=" + this.forBroadcasterId;

        JsonObject response = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(url)),
            RsonBodyHandler.of(JsonObject.class),
            this.auth
        ).body();

        if (response != null && !response.containsKey("data")) {
            throw new ApiException(response.toString());
        }

        return response
            .getArray("data")
            .getObject(0)
            .getString("stream_key");
    }

}
