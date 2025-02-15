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
import co.casterlabs.sdk.twitch.helix.types.HelixEventSubSubscription;
import co.casterlabs.sdk.twitch.helix.types.HelixEventSubSubscription.HelixEventSubStatus;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TwitchHelixGetEventSubSubscriptionsRequest extends AuthenticatedWebRequest<List<HelixEventSubSubscription>, TwitchHelixAuth> {
    private HelixEventSubStatus byStatus;
    private String byUserId;
    private String byType;

    /**
     * @param auth must be application auth if the subscription is a webhook or
     *             conduit, user auth otherwise.
     */
    public TwitchHelixGetEventSubSubscriptionsRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    @Override
    protected List<HelixEventSubSubscription> execute() throws ApiException, ApiAuthException, IOException {
        List<HelixEventSubSubscription> list = new LinkedList<>();
        String pageToken = null;
        while (true) {
            String url = "https://api.twitch.tv/helix/eventsub/subscriptions?" + new URIParameters()
                .optionalPut("status", this.byStatus == null ? null : this.byStatus.name().toLowerCase())
                .optionalPut("user_id", this.byUserId)
                .optionalPut("type", this.byType)
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

            list.addAll(Rson.DEFAULT.fromJson(response.get("data"), new TypeToken<List<HelixEventSubSubscription>>() {
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
