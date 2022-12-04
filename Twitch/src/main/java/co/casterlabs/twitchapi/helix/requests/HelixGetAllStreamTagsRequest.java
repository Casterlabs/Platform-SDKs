package co.casterlabs.twitchapi.helix.requests;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.twitchapi.HttpUtil;
import co.casterlabs.twitchapi.TwitchApi;
import co.casterlabs.twitchapi.helix.TwitchHelixAuth;
import co.casterlabs.twitchapi.helix.types.HelixStreamTag;
import lombok.NonNull;
import okhttp3.Response;

public class HelixGetAllStreamTagsRequest extends AuthenticatedWebRequest<List<HelixStreamTag>, TwitchHelixAuth> {

    public HelixGetAllStreamTagsRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    @Override
    public List<HelixStreamTag> execute() throws ApiException, ApiAuthException, IOException {
        List<HelixStreamTag> result = new LinkedList<>();

        String after = "";
        do {
            String url = "https://api.twitch.tv/helix/tags/streams?first=100&after=" + after;

            try (Response response = HttpUtil.sendHttpGet(url, null, this.auth)) {
                JsonObject json = TwitchApi.RSON.fromJson(response.body().string(), JsonObject.class);

                if (response.code() == 200) {
                    List<HelixStreamTag> tags = Rson.DEFAULT.fromJson(json.get("data"), new TypeToken<List<HelixStreamTag>>() {
                    });

                    result.addAll(tags);

                    JsonObject pagination = json.getObject("pagination");

                    if (pagination.containsKey("cursor")) {
                        after = pagination.get("cursor").getAsString();
                    } else {
                        break;
                    }
                } else {
                    throw new ApiException("Unable to get stream tags: " + json.get("message").getAsString());
                }
            }
        } while (true);

        return result;
    }

}
