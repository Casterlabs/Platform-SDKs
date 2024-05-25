package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.URIParameters;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TwitchHelixDeleteChatMessageRequest extends AuthenticatedWebRequest<Void, TwitchHelixAuth> {
    private @NonNull String forBroadcasterId;
    private @NonNull String moderatorId;
    private @Nullable String messageId;

    public TwitchHelixDeleteChatMessageRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    public TwitchHelixDeleteChatMessageRequest clearAll() {
        this.messageId = null;
        return this;
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.forBroadcasterId != null : "You must specify the broadcaster ID corresponding to where you want messages to be deleted from";
        assert this.moderatorId != null : "You must specify the moderator user ID corresponding to who you are authenticated as";

        String url = "https://api.twitch.tv/helix/moderation/chat?";

        if (this.messageId == null) {
            url += new URIParameters()
                .put("broadcaster_id", this.forBroadcasterId)
                .put("moderator_id", this.moderatorId);
        } else {
            url += new URIParameters()
                .put("broadcaster_id", this.forBroadcasterId)
                .put("moderator_id", this.moderatorId)
                .put("message_id", this.messageId);
        }

        JsonObject response = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE(),
            RsonBodyHandler.of(JsonObject.class),
            this.auth
        ).body();

        if (response != null && !response.containsKey("data")) {
            throw new ApiException(response.toString());
        }

        return null;
    }

}
