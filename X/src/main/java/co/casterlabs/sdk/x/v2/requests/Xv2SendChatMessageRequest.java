package co.casterlabs.sdk.x.v2.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.x.Xv2Auth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class Xv2SendChatMessageRequest extends AuthenticatedWebRequest<Void, Xv2Auth> {
    private String broadcastId;
    private String text;
    private String replyTo;

    public Xv2SendChatMessageRequest(@NonNull Xv2Auth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.broadcastId != null : "broadcastId is required";
        assert this.text != null : "text is required";

        JsonObject body = new JsonObject()
            .put("text", this.text);

        if (this.replyTo != null) {
            body.put("reply_to", this.replyTo);
        }

        String url = String.format("https://api.x.com/2/broadcasts/%s/chat", this.broadcastId);

        _ApiHelper.request(
            HttpRequest.newBuilder(URI.create(url))
                .POST(BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json"),
            this.auth
        );
        return null;
    }

}
