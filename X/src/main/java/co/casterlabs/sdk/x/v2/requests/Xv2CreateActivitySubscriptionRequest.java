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
public class Xv2CreateActivitySubscriptionRequest extends AuthenticatedWebRequest<Void, Xv2Auth> {
    private String userId;
    private String eventType;
    private String webhookId;

    public Xv2CreateActivitySubscriptionRequest(@NonNull Xv2Auth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.userId != null : "userId must be set";
        assert this.eventType != null : "eventType must be set";

        JsonObject body = new JsonObject()
            .put(
                "filter",
                new JsonObject()
                    .put("user_id", this.userId)
            )
            .put("event_type", this.eventType);

        if (this.webhookId != null) {
            body.put("webhook_id", this.webhookId);
        }

        String url = "https://api.x.com/2/activity/subscriptions";

        _ApiHelper.request(
            HttpRequest.newBuilder(URI.create(url))
                .POST(BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json"),
            this.auth
        );
        return null;
    }

}
