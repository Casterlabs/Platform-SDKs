package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
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
public class KickSendChatMessageRequest extends AuthenticatedWebRequest<Void, KickAuth> {
    private @Setter long chatRoomId = -1;
    private @Setter String message;

    public KickSendChatMessageRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.chatRoomId != -1 : "You must specify a chat channel to send the message to.";
        assert this.message != null : "You must specify a message to send.";

        JsonObject payload = new JsonObject()
            .put("type", "message")
            .put("content", this.message);

        WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(KickApi.API_BASE_URL + "/api/v2/messages/send/" + this.chatRoomId)
                .post(RequestBody.create(payload.toString().getBytes(StandardCharsets.UTF_8), MediaType.parse("application/json")))
                .header("Accept", "application/json"),
            this.auth
        );

        return null;
    }

}
