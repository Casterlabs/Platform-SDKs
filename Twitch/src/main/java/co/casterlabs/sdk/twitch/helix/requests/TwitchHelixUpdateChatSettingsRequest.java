package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.URIParameters;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TwitchHelixUpdateChatSettingsRequest extends AuthenticatedWebRequest<Void, TwitchHelixAuth> {
    private @NonNull String forBroadcasterId;
    private @NonNull String moderatorUserId;
    private @Setter(AccessLevel.NONE) JsonObject body = new JsonObject();

    public TwitchHelixUpdateChatSettingsRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    /* ---------------- */
    /* Emote-only       */
    /* ---------------- */

    public TwitchHelixUpdateChatSettingsRequest enableEmoteOnly() {
        this.body.put("emote_mode", true);
        return this;
    }

    public TwitchHelixUpdateChatSettingsRequest disableEmoteOnly() {
        this.body.put("emote_mode", false);
        return this;
    }

    /* ---------------- */
    /* Followers-only   */
    /* ---------------- */

    public TwitchHelixUpdateChatSettingsRequest enableFollowersOnly() {
        this.body.put("follower_mode", true);
        return this;
    }

    public TwitchHelixUpdateChatSettingsRequest enableFollowersOnly(int minutes) {
        assert minutes > 0 : "You must specify a non-zero amount of minutes";
        assert minutes <= 129600 : "You cannot specify a wait period longer than 3 months (129600 minutes)";
        this.body.put("follower_mode", true);
        this.body.put("follower_mode_duration", minutes);
        return this;
    }

    public TwitchHelixUpdateChatSettingsRequest disableFollowersOnly() {
        this.body.put("follower_mode", false);
        return this;
    }

    /* ---------------- */
    /* Slow mode        */
    /* ---------------- */

    public TwitchHelixUpdateChatSettingsRequest enableSlowMode() {
        this.body.put("slow_mode", true);
        return this;
    }

    public TwitchHelixUpdateChatSettingsRequest enableSlowMode(int seconds) {
        assert seconds > 0 : "You must specify a non-zero amount of seconds";
        assert seconds <= 120 : "You cannot specify a wait period longer than 2 minutes (120 seconds)";
        this.body.put("slow_mode", true);
        this.body.put("slow_mode_wait_time", seconds);
        return this;
    }

    public TwitchHelixUpdateChatSettingsRequest disableSlowMode() {
        this.body.put("slow_mode", false);
        return this;
    }

    /* ---------------- */
    /* Subscribers-only */
    /* ---------------- */

    public TwitchHelixUpdateChatSettingsRequest enableSubscribersOnly() {
        this.body.put("subscriber_mode", true);
        return this;
    }

    public TwitchHelixUpdateChatSettingsRequest disableSubscribersOnly() {
        this.body.put("subscriber_mode", false);
        return this;
    }

    /* ---------------- */
    /* Unique-messages  */
    /* ---------------- */

    public TwitchHelixUpdateChatSettingsRequest enableUniqueMessagesMode() {
        this.body.put("unique_chat_mode", true);
        return this;
    }

    public TwitchHelixUpdateChatSettingsRequest disableUniqueMessagesMode() {
        this.body.put("unique_chat_mode", false);
        return this;
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.forBroadcasterId != null : "You must specify the broadcaster ID corresponding to the authenticated user";
        assert this.moderatorUserId != null : "You must specify the target user ID corresponding to who you want to change moderator status for";

        String url = "https://api.twitch.tv/helix/chat/settings?" + new URIParameters()
            .put("broadcaster_id", this.forBroadcasterId)
            .put("moderator_id", this.moderatorUserId);

        JsonObject response = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("PATCH", BodyPublishers.ofString(this.body.toString()))
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
