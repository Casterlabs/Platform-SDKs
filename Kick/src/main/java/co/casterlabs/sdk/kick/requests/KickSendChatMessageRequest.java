package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

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
            .put("chatroom_id", this.chatRoomId)
            .put("message", this.message);

        WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(KickApi.API_BASE_URL + "/api/v1/chat-messages"))
                .POST(BodyPublishers.ofString(payload.toString()))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json"),
            BodyHandlers.discarding(),
            this.auth
        );

        return null;
    }

}
