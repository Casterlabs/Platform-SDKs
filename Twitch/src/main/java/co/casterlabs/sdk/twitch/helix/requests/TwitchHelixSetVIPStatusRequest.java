package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

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
public class TwitchHelixSetVIPStatusRequest extends AuthenticatedWebRequest<Void, TwitchHelixAuth> {
    private @NonNull String forBroadcasterId;
    private @NonNull String targetUserId;
    private boolean shouldBeVip = true;

    public TwitchHelixSetVIPStatusRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.forBroadcasterId != null : "You must specify the broadcaster ID corresponding to the authenticated user";
        assert this.targetUserId != null : "You must specify the target user ID corresponding to who you want to change VIP status for";

        JsonObject response;
        if (this.shouldBeVip) {
            JsonObject body = new JsonObject()
                .put("broadcaster_id", this.forBroadcasterId)
                .put("user_id", this.targetUserId);

            response = WebRequest.sendHttpRequest(
                HttpRequest.newBuilder()
                    .uri(URI.create("https://api.twitch.tv/helix/channels/vips"))
                    .POST(BodyPublishers.ofString(body.toString()))
                    .header("Content-Type", "application/json"),
                RsonBodyHandler.of(JsonObject.class),
                this.auth
            ).body();
        } else {
            String url = "https://api.twitch.tv/helix/channels/vips?" + new QueryBuilder()
                .put("broadcaster_id", this.forBroadcasterId)
                .put("user_id", this.targetUserId);

            response = WebRequest.sendHttpRequest(
                HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .DELETE(),
                RsonBodyHandler.of(JsonObject.class),
                this.auth
            ).body();
        }

        if (response != null && !response.containsKey("data")) {
            throw new ApiException(response.toString());
        }

        return null;
    }

}
