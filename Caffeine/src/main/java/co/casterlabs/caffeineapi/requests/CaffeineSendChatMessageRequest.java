package co.casterlabs.caffeineapi.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.caffeineapi.CaffeineAuth;
import co.casterlabs.caffeineapi.CaffeineEndpoints;
import co.casterlabs.caffeineapi.HttpUtil;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Setter
@Accessors(chain = true)
public class CaffeineSendChatMessageRequest extends AuthenticatedWebRequest<Void, CaffeineAuth> {
    private @NonNull String stageId;
    private @NonNull String message;

    public CaffeineSendChatMessageRequest(CaffeineAuth auth) {
        super(auth);
    }

    public CaffeineSendChatMessageRequest setMessage(@NonNull String message) {
        if (message.length() > 80) {
            throw new IllegalArgumentException("Message length cannot exceed 80 characters.");
        }

        this.message = message;

        return this;
    }

    public CaffeineSendChatMessageRequest setCAID(@NonNull String caid) {
        this.stageId = caid.substring(4);
        return this;
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.message != null : "Message must be set.";
        assert this.stageId != null : "Stage ID must be set.";

        JsonObject post = new JsonObject()
            .put("type", "reaction")
            .put("publisher", this.auth.getSignedToken())
            .put("body", JsonObject.singleton("text", this.message));

        try (Response response = HttpUtil.sendHttp(
            post.toString(),
            String.format(CaffeineEndpoints.CHAT_MESSAGE, this.stageId),
            this.auth,
            "application/json"
        )) {
            if (response.code() == 401) {
                throw new ApiAuthException("Auth is invalid");
            }

            return null;
        }
    }

}
