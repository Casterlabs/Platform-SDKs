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

@Accessors(chain = true, fluent = true)
public class KickPostModerationBanRequest extends AuthenticatedWebRequest<KickPostedChatMessage, KickAuth> {
    private @Setter Integer forChannelId = null;
    private @Setter Integer forUserId = null;
    private @Setter Integer withTimeoutDurationInMinutes = null;
    private @Setter String withReason = null;

    public KickPostModerationBanRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected KickPostedChatMessage execute() throws ApiException, ApiAuthException, IOException {
        String url = "https://api.kick.com/public/v1/moderation/bans";

        JsonObject payload = new JsonObject()
            .put("broadcaster_user_id", this.forChannelId)
            .put("user_id", this.forUserId)
            .put("duration", this.withTimeoutDurationInMinutes)
            .put("reason", this.withReason);

        return _KickApi.request(
            HttpRequest.newBuilder(URI.create(url))
                .POST(BodyPublishers.ofString(payload.toString()))
                .header("Content-Type", "application/json"),
            this.auth,
            KickPostedChatMessage.class
        );
    }

}
