package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TwitchHelixSendAnnouncementRequest extends AuthenticatedWebRequest<Void, TwitchHelixAuth> {
    private @NonNull String forBroadcasterId;
    private @NonNull String moderatorId;
    private @NonNull String message;

    public TwitchHelixSendAnnouncementRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.forBroadcasterId != null : "You must specify the broadcaster ID corresponding to where you want the announcement to appear";
        assert this.moderatorId != null : "You must specify the moderator user ID corresponding to who you are authenticated as";
        assert this.message != null : "You must specify a message";

        JsonObject body = new JsonObject()
            .put("message", this.message);
        // TODO support the color param

        String url = "https://api.twitch.tv/helix/chat/announcements?" + new QueryBuilder()
            .put("broadcaster_id", this.forBroadcasterId)
            .put("moderator_id", this.moderatorId);

        JsonObject response = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(url))
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
