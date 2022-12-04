package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.HttpUtil;
import co.casterlabs.sdk.twitch.TwitchApi;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import co.casterlabs.sdk.twitch.helix.types.HelixSubscriber;
import co.casterlabs.sdk.twitch.helix.types.HelixUser;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Accessors(chain = true)
public class HelixGetUserSubscribersRequest extends AuthenticatedWebRequest<List<HelixSubscriber>, TwitchHelixAuth> {
    private @Setter String id;

    public HelixGetUserSubscribersRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    public HelixGetUserSubscribersRequest setUser(@NonNull HelixUser user) {
        this.id = user.getId();
        return this;
    }

    @Override
    public List<HelixSubscriber> execute() throws ApiException, IOException {
        assert this.id != null : "ID must be set.";

        List<HelixSubscriber> subscribers = new ArrayList<>();

        String after = "";
        while (true) {
            String url = String.format("https://api.twitch.tv/helix/subscriptions?first=100&broadcaster_id=%s&after=%s", this.id, after);

            try (Response response = HttpUtil.sendHttpGet(url, null, this.auth)) {
                JsonObject json = TwitchApi.RSON.fromJson(response.body().string(), JsonObject.class);

                response.close();

                if (response.code() == 200) {
                    JsonArray data = json.getArray("data");

                    for (JsonElement e : data) {
                        HelixSubscriber subscriber = TwitchApi.RSON.fromJson(e, HelixSubscriber.class);

                        if (subscriber.getUserId() != this.id) {
                            subscribers.add(subscriber);
                        }
                    }

                    JsonObject pagination = json.getObject("pagination");

                    if (pagination.containsKey("cursor")) {
                        after = pagination.get("cursor").getAsString();
                    } else {
                        break;
                    }
                } else {
                    throw new ApiException("Unable to get subscribers: " + json.get("message").getAsString());
                }
            }
        }

        return subscribers;
    }

}
