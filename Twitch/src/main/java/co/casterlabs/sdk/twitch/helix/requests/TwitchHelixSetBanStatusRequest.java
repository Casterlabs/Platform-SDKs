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
import co.casterlabs.apiutil.web.URIParameters;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TwitchHelixSetBanStatusRequest extends AuthenticatedWebRequest<Void, TwitchHelixAuth> {
    private @NonNull String forBroadcasterId;
    private @NonNull String moderatorId;
    private @NonNull String targetUserId;

    private @Nullable String reason;

    /**
     * Leave null for infinity or set to a value less than 1209600 seconds for a
     * "timeout".
     */
    private @Nullable Integer durationSeconds;

    private boolean shouldBeBanned = true;

    public TwitchHelixSetBanStatusRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.forBroadcasterId != null : "You must specify the broadcaster ID corresponding to where you want the ban to apply";
        assert this.moderatorId != null : "You must specify the moderator user ID corresponding to who you are authenticated as";
        assert this.targetUserId != null : "You must specify the target user ID corresponding to who you want to change the ban status for";
        assert this.durationSeconds == null || (this.durationSeconds > 0 && this.durationSeconds <= 1209600) : "Duration (if specified) must be greater than 0 and smaller than 1209600 seconds (2 weeks)";

        JsonObject response;
        if (this.shouldBeBanned) {
            String url = "https://api.twitch.tv/helix/moderation/bans?" + new URIParameters()
                .put("broadcaster_id", this.forBroadcasterId)
                .put("moderator_id", this.moderatorId);

            JsonObject body = new JsonObject()
                .put("user_id", this.targetUserId);

            if (this.durationSeconds != null) {
                body.put("duration", this.durationSeconds);
            }
            if (this.reason != null) {
                body.put("reason", this.reason);
            }

            response = WebRequest.sendHttpRequest(
                HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(BodyPublishers.ofString(JsonObject.singleton("data", body).toString()))
                    .header("Content-Type", "application/json"),
                RsonBodyHandler.of(JsonObject.class),
                this.auth
            ).body();
        } else {
            String url = "https://api.twitch.tv/helix/moderation/bans?" + new URIParameters()
                .put("broadcaster_id", this.forBroadcasterId)
                .put("moderator_id", this.moderatorId)
                .put("user_id", this.targetUserId);

            response = WebRequest.sendHttpRequest(
                HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .DELETE(),
                RsonBodyHandler.of(JsonObject.class),
                this.auth
            ).body();
        }

        if (response != null && !response.containsKey("data")) {
            throw new ApiException(response.toString());
        }

        return null;
    }

}
