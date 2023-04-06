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
public class KickDeleteChatMessageRequest extends AuthenticatedWebRequest<Void, KickAuth> {
    private @Setter long chatRoomId = -1;
    private @Setter String messageId;

    public KickDeleteChatMessageRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.chatRoomId != -1 : "You must specify a chat channel.";
        assert this.messageId != null : "You must specify a message ID to delete.";

        JsonObject payload = new JsonObject()
            .put("id", this.messageId)
            .put("deleted", true)
            .put("chatroom_id", this.chatRoomId);

        WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(KickApi.API_BASE_URL + "/api/v1/chat-messages/" + this.messageId)
                .post(RequestBody.create(payload.toString().getBytes(StandardCharsets.UTF_8), MediaType.parse("application/json")))
                .header("Accept", "application/json"),
            this.auth
        );

        return null;
    }

}
