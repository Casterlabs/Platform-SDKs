package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.URIParameters;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TwitchHelixSetRaidStatusRequest extends AuthenticatedWebRequest<Void, TwitchHelixAuth> {
    private @NonNull String fromBroadcasterId;
    private @Nullable String toBroadcasterId;
    private boolean shouldRaid = true;

    public TwitchHelixSetRaidStatusRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.fromBroadcasterId != null : "You must specify the broadcaster ID corresponding to the authenticated user";

        JsonObject response;
        if (this.shouldRaid) {
            assert this.toBroadcasterId != null : "You must specify the target broadcaster ID corresponding to who you want to raid";

            String url = "https://api.twitch.tv/helix/raids?" + new URIParameters()
                .put("from_broadcaster_id", this.fromBroadcasterId)
                .put("to_broadcaster_id", this.toBroadcasterId);

            response = WebRequest.sendHttpRequest(
                HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(BodyPublishers.noBody()),
                RsonBodyHandler.of(JsonObject.class),
                this.auth
            ).body();
        } else {
            String url = "https://api.twitch.tv/helix/raids?" + new URIParameters()
                .put("broadcaster_id", this.fromBroadcasterId);

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
