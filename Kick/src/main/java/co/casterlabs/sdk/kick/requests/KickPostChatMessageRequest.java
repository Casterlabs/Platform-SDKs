package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.KickAuth;
import co.casterlabs.sdk.kick.types.KickPostedChatMessage;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class KickPostChatMessageRequest extends AuthenticatedWebRequest<KickPostedChatMessage, KickAuth> {
    private @Setter Integer forChannelId = null;
    private @Setter Integer withContent = null;
    private @Setter boolean asBot = false;

    public KickPostChatMessageRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected KickPostedChatMessage execute() throws ApiException, ApiAuthException, IOException {
        // When posting as bot, it's always to the current channel.
        if (!this.asBot) {
            assert this.forChannelId != null : "You must specify a channel id.";
        }
        assert this.withContent != null : "You must specify content to post.";

        String url = "https://api.kick.com/public/v1/chat";

        JsonObject payload = new JsonObject()
            .put("broadcaster_user_id", this.forChannelId)
            .put("content", this.withContent)
            .put("type", this.asBot ? "bot" : "user");

        return _KickApi.request(
            HttpRequest.newBuilder(URI.create(url))
                .POST(BodyPublishers.ofString(payload.toString())),
            this.auth,
            KickPostedChatMessage.class
        );
    }

}
