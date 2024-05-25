package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.LinkedList;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.URIParameters;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import co.casterlabs.sdk.twitch.helix.types.HelixSimpleUser;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TwitchHelixGetChannelVIPsRequest extends AuthenticatedWebRequest<List<HelixSimpleUser>, TwitchHelixAuth> {
    private @NonNull String forBroadcasterId;

    public TwitchHelixGetChannelVIPsRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    @Override
    protected List<HelixSimpleUser> execute() throws ApiException, ApiAuthException, IOException {
        assert !this.auth.isApplicationAuth() : "You cannot use Application Auth.";
        assert this.forBroadcasterId != null : "You must specify the broadcaster ID corresponding to the authenticated user";

        List<HelixSimpleUser> list = new LinkedList<>();
        String pageToken = null;
        while (true) {
            String url = "https://api.twitch.tv/helix/channels/vips?" + new URIParameters()
                .put("broadcaster_id", this.forBroadcasterId)
                .put("first", "100")
                .optionalPut("after", pageToken);

            JsonObject response = WebRequest.sendHttpRequest(
                HttpRequest.newBuilder()
                    .uri(URI.create(url)),
                RsonBodyHandler.of(JsonObject.class),
                this.auth
            ).body();

            if (response != null && !response.containsKey("data")) {
                throw new ApiException(response.toString());
            }

            list.addAll(Rson.DEFAULT.fromJson(response.get("data"), new TypeToken<List<HelixSimpleUser>>() {
            }));

            if (response.containsKey("pagination") && response.getObject("pagination").containsKey("cursor")) {
                pageToken = response.getObject("pagination").getString("cursor");
            } else {
                break;
            }
        }
        return list;
    }

}
