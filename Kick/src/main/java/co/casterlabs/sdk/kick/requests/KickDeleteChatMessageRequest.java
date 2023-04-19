package co.casterlabs.sdk.kick.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.kick.KickApi;
import co.casterlabs.sdk.kick.KickAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Request;

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

        WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(KickApi.API_BASE_URL + "/api/v2/chatrooms/" + this.chatRoomId + "/messages/" + this.messageId)
                .delete()
                .header("Accept", "application/json"),
            this.auth
        );

        return null;
    }

}
