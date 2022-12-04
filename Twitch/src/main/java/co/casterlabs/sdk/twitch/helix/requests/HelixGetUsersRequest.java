package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
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
import co.casterlabs.sdk.twitch.helix.types.HelixUser;
import lombok.NonNull;
import okhttp3.Response;

public class HelixGetUsersRequest extends AuthenticatedWebRequest<List<HelixUser>, TwitchHelixAuth> {
    private Set<String> logins = new HashSet<>();
    private Set<String> ids = new HashSet<>();

    public HelixGetUsersRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    public HelixGetUsersRequest addLogin(@NonNull String login) {
        this.logins.add(login);
        return this;
    }

    public HelixGetUsersRequest addId(@NonNull String id) {
        this.ids.add(id);
        return this;
    }

    @Override
    public List<HelixUser> execute() throws ApiException, ApiAuthException, IOException {
        // Build the URL.
        StringBuilder sb = new StringBuilder("https://api.twitch.tv/helix/users?");

        this.ids.forEach((id) -> sb.append("&id=").append(id));
        this.logins.forEach((login) -> sb.append("&login=").append(login));

        String url = sb.toString().replaceFirst("&", "");

        // Request it.
        try (Response response = HttpUtil.sendHttpGet(url, null, auth)) {
            JsonObject json = TwitchApi.RSON.fromJson(response.body().string(), JsonObject.class);

            if (response.code() == 200) {
                return Rson.DEFAULT.fromJson(
                    json.get("data"),
                    new TypeToken<List<HelixUser>>() {
                    }
                );
            } else {
                throw new ApiException("Unable to get users: " + json.get("message").getAsString());
            }
        }
    }

}
