package co.casterlabs.sdk.x.v2.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.x.Xv2Auth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class Xv2DeleteChatMessageRequest extends AuthenticatedWebRequest<Void, Xv2Auth> {
    private String broadcastId;
    private String messageId;

    public Xv2DeleteChatMessageRequest(@NonNull Xv2Auth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.broadcastId != null : "broadcastId is required";
        assert this.messageId != null : "messageId is required";

        String url = String.format("https://api.x.com/2/broadcasts/%s/chat/%s", this.broadcastId, this.messageId);

        _ApiHelper.request(
            HttpRequest.newBuilder(URI.create(url))
                .DELETE(),
            this.auth
        );
        return null;
    }

}
