package co.casterlabs.sdk.x.v2.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.x.Xv2Auth;
import co.casterlabs.sdk.x.v2.types.Xv2Broadcast;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class Xv2CreateBroadcastRequest extends AuthenticatedWebRequest<Xv2Broadcast, Xv2Auth> {
    private String userId;
    private String sourceId;
    private String region;
    private boolean isLowLatency = true;

    public Xv2CreateBroadcastRequest(@NonNull Xv2Auth auth) {
        super(auth);
    }

    @Override
    protected Xv2Broadcast execute() throws ApiException, ApiAuthException, IOException {
        assert this.userId != null : "userId must be set";
        assert this.sourceId != null : "sourceId must be set";
        assert this.region != null : "region must be set";

        JsonObject body = new JsonObject()
            .put("source_id", this.sourceId)
            .put("region", this.region)
            .put("is_low_latency", this.isLowLatency);

        String url = String.format("https://api.x.com/2/users/%s/broadcasts", this.userId);

        return _ApiHelper.request(
            HttpRequest.newBuilder(URI.create(url))
                .POST(BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json"),
            this.auth,
            ResponseHolder.class
        ).broadcast;
    }

    @JsonClass(exposeAll = true)
    private static class ResponseHolder {
        public final Xv2Broadcast broadcast = null;

    }

}
