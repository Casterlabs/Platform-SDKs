package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TwitchHelixGetChannelSubscriberCountRequest extends AuthenticatedWebRequest<Long, TwitchHelixAuth> {
    private @NonNull String forBroadcasterId;

    public TwitchHelixGetChannelSubscriberCountRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    @Override
    protected Long execute() throws ApiException, ApiAuthException, IOException {
        assert !this.auth.isApplicationAuth() : "You cannot use Application Auth.";
        assert this.forBroadcasterId != null : "You must specify the broadcaster ID corresponding to the authenticated user";

        String url = "https://api.twitch.tv/helix/subscriptions?" + new QueryBuilder()
            .put("broadcaster_id", this.forBroadcasterId)
            .put("first", "1");

        JsonObject response = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(url)),
            RsonBodyHandler.of(JsonObject.class),
            this.auth
        ).body();

        if (response != null && !response.containsKey("data")) {
            throw new ApiException(response.toString());
        }

        return response.getNumber("total").longValue();
    }

}
