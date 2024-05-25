package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TwitchHelixCreateStreamMarkerRequest extends AuthenticatedWebRequest<Void, TwitchHelixAuth> {
    private @NonNull String forBroadcasterId;
    private @Nullable String text;

    public TwitchHelixCreateStreamMarkerRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.forBroadcasterId != null : "You must specify the broadcaster ID corresponding to where you want the shoutout to appear";

        JsonObject body = new JsonObject()
            .put("user_id", this.forBroadcasterId);

        if (this.text != null) {
            assert this.text.length() <= 140 : "Text cannot be larger than 140 characters.";
            body.put("description", this.text);
        }

        JsonObject response = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create("https://api.twitch.tv/helix/streams/markers"))
                .POST(BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json"),
            RsonBodyHandler.of(JsonObject.class),
            this.auth
        ).body();

        if (response != null && !response.containsKey("data")) {
            throw new ApiException(response.toString());
        }

        return null;
    }

}
