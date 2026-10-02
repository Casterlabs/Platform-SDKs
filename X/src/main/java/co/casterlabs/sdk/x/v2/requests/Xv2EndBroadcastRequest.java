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
public class Xv2EndBroadcastRequest extends AuthenticatedWebRequest<Void, Xv2Auth> {
    private String userId;
    private String broadcastId;

    public Xv2EndBroadcastRequest(@NonNull Xv2Auth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.userId != null : "userId must be set";
        assert this.broadcastId != null : "broadcastId must be set";

        JsonObject body = new JsonObject()
            .put("state", "END");

        String url = String.format("https://api.x.com/2/users/%s/broadcasts/%s/state", this.userId, this.broadcastId);

        _ApiHelper.request(
            HttpRequest.newBuilder(URI.create(url))
                .PUT(BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json"),
            this.auth
        );
        return null;
    }

}
