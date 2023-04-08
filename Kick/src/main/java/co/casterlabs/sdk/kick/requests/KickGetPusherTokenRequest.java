package co.casterlabs.sdk.kick.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.KickApi;
import co.casterlabs.sdk.kick.KickAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

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

        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .post(RequestBody.create("socket_id=" + this.socketId + "&channel_name=" + this.pusherChannel, MediaType.parse("application/x-www-form-urlencoded")))
                .url(KickApi.API_BASE_URL + "/broadcasting/auth") // Pretty silly name for this, if you ask me.
                .header("Accept", "application/json"),
            this.auth
        );

        JsonObject body = Rson.DEFAULT.fromJson(response, JsonObject.class);

        if (body.containsKey("auth")) {
            return body.toString();
        } else {
            throw new ApiAuthException("You cannot authorize for that channel.");
        }
    }

}
