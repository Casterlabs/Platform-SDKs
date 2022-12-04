package co.casterlabs.sdk.trovo.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.trovo.TrovoAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Request;

@Setter
@NonNull
@Accessors(chain = true)
public class TrovoDeleteChatMessageRequest extends AuthenticatedWebRequest<Void, TrovoAuth> {
    public static final String URL = "https://open-api.trovo.live/openplatform/channels/%s/messages/%s/senderuid/%s";

    private @Setter String channelId;
    private @Setter String messageId;
    private @Setter String senderId;

    public TrovoDeleteChatMessageRequest(@NonNull TrovoAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.channelId != null : "You must set a channel id.";
        assert this.messageId != null : "You must set a message id.";
        assert this.senderId != null : "You must set a sender id.";
        assert !this.auth.isApplicationAuth() : "You must use user auth for this request.";

        WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(String.format(URL, this.channelId, this.messageId, this.senderId))
                .delete(),
            this.auth
        );

        return null;
    }

}
