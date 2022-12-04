package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.HttpUtil;
import co.casterlabs.sdk.twitch.TwitchApi;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import co.casterlabs.sdk.twitch.helix.types.HelixStreamTag;
import co.casterlabs.sdk.twitch.helix.types.HelixUser;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@NonNull
@Accessors(chain = true)
public class HelixReplaceStreamTagsRequest extends AuthenticatedWebRequest<Void, TwitchHelixAuth> {
    private @Setter String broadcasterId;
    private Set<String> tags = new HashSet<>();

    public HelixReplaceStreamTagsRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    public HelixReplaceStreamTagsRequest setUser(@NonNull HelixUser broadcaster) {
        this.broadcasterId = broadcaster.getId();
        return this;
    }

    public HelixReplaceStreamTagsRequest addTag(@NonNull HelixStreamTag tag) {
        this.tags.add(tag.getId());
        return this;
    }

    public HelixReplaceStreamTagsRequest addTagId(@NonNull String tagId) {
        this.tags.add(tagId);
        return this;
    }

    @Override
    public Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.broadcasterId != null : "Broadcaster ID must be set.";

        String url = "https://api.twitch.tv/helix/streams/tags?broadcaster_id=" + this.broadcasterId;

        JsonObject body = new JsonObject()
            .put("tag_ids", Rson.DEFAULT.toJson(this.tags));

        try (Response response = HttpUtil.sendHttp(body.toString(), "PUT", url, null, "application/json", this.auth)) {
            if (!response.isSuccessful()) {
                JsonObject json = TwitchApi.RSON.fromJson(response.body().string(), JsonObject.class);

                throw new ApiException("Unable to replace stream tags: " + json.get("message").getAsString());
            }
        }

        return null;
    }

}
