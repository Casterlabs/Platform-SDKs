package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.HttpUtil;
import co.casterlabs.sdk.twitch.TwitchApi;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import co.casterlabs.sdk.twitch.helix.types.HelixChannelInformation;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Setter
@NonNull
@Accessors(chain = true)
public class HelixGetChannelInformationRequest extends AuthenticatedWebRequest<List<HelixChannelInformation>, TwitchHelixAuth> {
    private Set<String> ids = new HashSet<>();

    public HelixGetChannelInformationRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    public HelixGetChannelInformationRequest addId(@NonNull String id) {
        this.ids.add(id);
        return this;
    }

    @Override
    public List<HelixChannelInformation> execute() throws ApiException, ApiAuthException, IOException {
        if (this.ids.isEmpty()) {
            return new ArrayList<>();
        }

        // Build the url
        StringBuilder sb = new StringBuilder("https://api.twitch.tv/helix/streams?");

        this.ids.forEach((id) -> sb.append("&broadcaster_id=").append(id));

        String url = sb.toString().replaceFirst("&", "");

        // Request it.
        try (Response response = HttpUtil.sendHttpGet(url, null, auth)) {
            JsonObject json = TwitchApi.RSON.fromJson(response.body().string(), JsonObject.class);

            if (response.code() == 200) {
                return Rson.DEFAULT.fromJson(
                    json.get("data"),
                    new TypeToken<List<HelixChannelInformation>>() {
                    }
                );
            } else {
                throw new ApiException("Unable to get channel information: " + json.get("message").getAsString());
            }
        }
    }

}
