package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.KickApi;
import co.casterlabs.sdk.kick.KickAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class KickGetPusherTokenRequest extends AuthenticatedWebRequest<String, KickAuth> {
    private @Setter String pusherChannel;
    private @Setter String socketId;

    public KickGetPusherTokenRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected String execute() throws ApiException, ApiAuthException, IOException {
        assert this.pusherChannel != null : "You must specify a pusher channel to authorize for.";
        assert this.pusherChannel != null : "You must specify a socket ID to authorize for.";

        JsonObject body = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(KickApi.API_BASE_URL + "/broadcasting/auth")) // Pretty silly name for this, if you ask me.
                .POST(BodyPublishers.ofString("socket_id=" + this.socketId + "&channel_name=" + this.pusherChannel))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json"),
            RsonBodyHandler.of(JsonObject.class),
            this.auth
        ).body();

        if (body.containsKey("auth")) {
            return body.toString();
        } else {
            throw new ApiAuthException("You cannot authorize for that channel.");
        }
    }

}
