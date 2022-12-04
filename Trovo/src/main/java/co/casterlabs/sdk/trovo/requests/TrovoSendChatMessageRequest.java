package co.casterlabs.sdk.trovo.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.trovo.TrovoAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

@Setter
@NonNull
@Accessors(chain = true)
public class TrovoSendChatMessageRequest extends AuthenticatedWebRequest<Void, TrovoAuth> {
    public static final String URL = "https://open-api.trovo.live/openplatform/chat/send";

    private String message;
    private String channelId;

    public TrovoSendChatMessageRequest(@NonNull TrovoAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.message != null : "You must set a message.";
        assert !this.auth.isApplicationAuth() : "You must use user auth for this request.";

        JsonObject body = new JsonObject()
            .put("content", this.message);

        if (this.channelId != null) {
            body.put("channel_id", this.channelId);
        }

        WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(URL)
                .post(
                    RequestBody.create(
                        body.toString(),
                        MediaType.get("application/json")
                    )
                ),
            this.auth
        );

        return null;
    }

}
