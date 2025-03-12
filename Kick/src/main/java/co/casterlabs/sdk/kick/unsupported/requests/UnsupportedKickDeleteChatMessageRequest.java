package co.casterlabs.sdk.kick.unsupported.requests;

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
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickApi;
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class UnsupportedKickDeleteChatMessageRequest extends AuthenticatedWebRequest<Void, UnsupportedKickAuth> {
    private @Setter long chatRoomId = -1;
    private @Setter String messageId;

    public UnsupportedKickDeleteChatMessageRequest(@NonNull UnsupportedKickAuth auth) {
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
            HttpRequest.newBuilder()
                .uri(URI.create(UnsupportedKickApi.API_BASE_URL + "/api/v1/chat-messages/" + this.messageId))
                .POST(BodyPublishers.ofString(payload.toString()))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json"),
            BodyHandlers.discarding(),
            this.auth
        );

        return null;
    }

}
