package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.HttpUtil;
import co.casterlabs.sdk.twitch.TwitchApi;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import co.casterlabs.sdk.twitch.helix.types.HelixUser;
import co.casterlabs.sdk.twitch.thirdparty.types.TwitchGame;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Setter
@NonNull
@Accessors(chain = true)
public class HelixModifyChannelInformationRequest extends AuthenticatedWebRequest<Void, TwitchHelixAuth> {
    private String broadcasterId;

    private String title;
    private String language = "other";
    private String gameId = "0";

    public HelixModifyChannelInformationRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    public HelixModifyChannelInformationRequest setUser(@NonNull HelixUser broadcaster) {
        this.broadcasterId = broadcaster.getId();
        return this;
    }

    public HelixModifyChannelInformationRequest setGame(@NonNull TwitchGame game) {
        this.gameId = game.getId();
        return this;
    }

    @Override
    public Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.broadcasterId != null : "Broadcaster ID must be set.";
        assert this.title != null : "Title must be set.";

        String url = "https://api.twitch.tv/helix/channels?broadcaster_id=" + this.broadcasterId;

        JsonObject body = new JsonObject()
            .put("game_id", this.gameId)
            .put("broadcaster_language", this.language)
            .put("title", this.title);
        // Delay is unsupported.

        try (Response response = HttpUtil.sendHttp(body.toString(), "PATCH", url, null, "application/json", this.auth)) {
            if (!response.isSuccessful()) {
                JsonObject json = TwitchApi.RSON.fromJson(response.body().string(), JsonObject.class);

                throw new ApiException("Unable to modify channel information: " + json.get("message").getAsString());
            }
        }

        return null;
    }

}
