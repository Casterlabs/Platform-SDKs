package co.casterlabs.caffeineapi.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.caffeineapi.CaffeineAuth;
import co.casterlabs.caffeineapi.CaffeineEndpoints;
import co.casterlabs.caffeineapi.HttpUtil;
import co.casterlabs.caffeineapi.realtime.messages.ChatEvent;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Setter
@Accessors(chain = true)
public class CaffeineUpvoteChatMessageRequest extends AuthenticatedWebRequest<Void, CaffeineAuth> {
    private @NonNull String messageId;

    public CaffeineUpvoteChatMessageRequest(CaffeineAuth auth) {
        super(auth);
    }

    public void setMessage(ChatEvent event) {
        this.messageId = event.getId();
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.messageId != null : "Message ID must be set.";

        try (Response response = HttpUtil.sendHttp(
            "{}",
            String.format(CaffeineEndpoints.UPVOTE_MESSAGE, this.messageId),
            this.auth,
            "application/json"
        )) {
            if (response.code() == 401) {
                throw new ApiAuthException("Auth is invalid");
            } else if (response.code() == 401) {
                throw new ApiException("Unable to upvote a chat message due to an authentication error");
            } else if (response.code() == 400) {
                throw new IllegalArgumentException("Message id is invalid");
            }

            return null;
        }
    }

}
