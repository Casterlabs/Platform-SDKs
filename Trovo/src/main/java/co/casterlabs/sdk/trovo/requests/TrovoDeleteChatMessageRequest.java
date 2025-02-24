package co.casterlabs.sdk.trovo.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.trovo.TrovoAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TrovoDeleteChatMessageRequest extends AuthenticatedWebRequest<Void, TrovoAuth> {
    public static final String URL = "https://open-api.trovo.live/openplatform/channels/%s/messages/%s/senderuid/%s";

    private String forChannelId;
    private String forSenderId;
    private String withMessageId;

    public TrovoDeleteChatMessageRequest(@NonNull TrovoAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.forChannelId != null : "You must set a channel id.";
        assert this.forSenderId != null : "You must set a sender id.";
        assert this.withMessageId != null : "You must set a message id.";
        assert !this.auth.isApplicationAuth() : "You must use user auth for this request.";

        WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(String.format(URL, this.forChannelId, this.withMessageId, this.forSenderId)))
                .DELETE(),
            BodyHandlers.discarding(),
            this.auth
        );

        return null;
    }

}
