package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.KickAuth;
import co.casterlabs.sdk.kick.types.KickCreatedWebhook;
import co.casterlabs.sdk.kick.webhook.KickWebhookEvent;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class KickCreateWebhookRequest extends AuthenticatedWebRequest<KickCreatedWebhook, KickAuth> {
    private @Setter KickWebhookEvent.Type withType = null;

    public KickCreateWebhookRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected KickCreatedWebhook execute() throws ApiException, ApiAuthException, IOException {
        assert this.withType != null : "You must specify a type.";

        String url = "https://api.kick.com/public/v1/events/subscriptions";

        JsonObject payload = new JsonObject()
            .put(
                "events",
                JsonArray.of(
                    new JsonObject()
                        .put("name", this.withType.type)
                        .put("version", this.withType.version)
                )
            )
            .put("method", "webhook");

        return _KickApi.request(
            HttpRequest.newBuilder(URI.create(url))
                .POST(BodyPublishers.ofString(payload.toString())),
            this.auth,
            KickCreatedWebhook[].class
        )[0];
    }

}
