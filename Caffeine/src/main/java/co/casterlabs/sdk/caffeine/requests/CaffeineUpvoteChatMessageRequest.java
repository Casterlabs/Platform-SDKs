package co.casterlabs.sdk.caffeine.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.caffeine.CaffeineAuth;
import co.casterlabs.sdk.caffeine.CaffeineEndpoints;
import co.casterlabs.sdk.caffeine.HttpUtil;
import co.casterlabs.sdk.caffeine.realtime.messages.ChatEvent;
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
