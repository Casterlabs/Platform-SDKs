package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.HttpUtil;
import co.casterlabs.sdk.twitch.TwitchApi;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import co.casterlabs.sdk.twitch.helix.requests.HelixGetUserFollowersRequest.HelixFollowersResult;
import co.casterlabs.sdk.twitch.helix.types.HelixFollower;
import co.casterlabs.sdk.twitch.helix.types.HelixUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Accessors(chain = true)
public class HelixGetUserFollowersRequest extends AuthenticatedWebRequest<HelixFollowersResult, TwitchHelixAuth> {
    private @Setter boolean getAll = false;
    private @Setter int first = 20;
    private @Setter @NonNull String id;

    public HelixGetUserFollowersRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    public HelixGetUserFollowersRequest setUser(@NonNull HelixUser user) {
        this.id = user.getId();
        return this;
    }

    @Override
    public HelixFollowersResult execute() throws ApiException, IOException {
        assert this.id != null : "ID must be set.";

        List<HelixFollower> followers = new ArrayList<>();
        long total = 0;

        String after = "";
        do {
            String url = String.format("https://api.twitch.tv/helix/users/follows?first=%d&to_id=%s&after=%s", this.first, this.id, after);

            try (Response response = HttpUtil.sendHttpGet(url, null, this.auth)) {
                JsonObject json = TwitchApi.RSON.fromJson(response.body().string(), JsonObject.class);

                if (response.code() == 200) {
                    JsonArray data = json.getArray("data");

                    total = json.get("total").getAsNumber().intValue();

                    for (JsonElement e : data) {
                        followers.add(TwitchApi.RSON.fromJson(e, HelixFollower.class));
                    }

                    JsonObject pagination = json.getObject("pagination");

                    if (pagination.containsKey("cursor")) {
                        after = pagination.get("cursor").getAsString();
                    } else {
                        break;
                    }
                } else {
                    throw new ApiException("Unable to get followers: " + json.get("message").getAsString());
                }
            }
        } while (this.getAll);

        return new HelixFollowersResult(followers, total);
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @JsonClass(exposeAll = true)
    public static class HelixFollowersResult {
        private List<HelixFollower> followers;
        private long total;

    }

}
