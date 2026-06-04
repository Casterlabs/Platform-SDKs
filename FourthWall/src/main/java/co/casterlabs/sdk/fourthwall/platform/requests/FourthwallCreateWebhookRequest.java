package co.casterlabs.sdk.fourthwall.platform.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.fourthwall.FourthwallAuth;
import co.casterlabs.sdk.fourthwall.platform.types.FourthwallWebhook;
import co.casterlabs.sdk.fourthwall.webhook.FourthwallWebhookType;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class FourthwallCreateWebhookRequest extends AuthenticatedWebRequest<FourthwallWebhook, FourthwallAuth> {
    private @NonNull String url;
    private @NonNull FourthwallWebhookType[] allowedTypes;

    public FourthwallCreateWebhookRequest(@NonNull FourthwallAuth auth) {
        super(auth);
    }

    public FourthwallCreateWebhookRequest allowedTypes(@NonNull FourthwallWebhookType... allowedTypes) {
        assert allowedTypes.length > 0 : "You must specify at least one allowed type.";
        this.allowedTypes = allowedTypes;
        return this;
    }

    @Override
    protected FourthwallWebhook execute() throws ApiException, ApiAuthException, IOException {
        assert this.url != null : "You must specify the URL to send the webhook to.";
        assert this.allowedTypes != null && this.allowedTypes.length > 0 : "You must specify the allowed types for the webhook.";

        JsonObject body = new JsonObject()
            .put("url", this.url)
            .put("allowedTypes", Rson.DEFAULT.toJson(this.allowedTypes));

        HttpResponse<JsonObject> response = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create("https://api.fourthwall.com/open-api/v1.0/webhooks"))
                .POST(BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json"),
            RsonBodyHandler.of(JsonObject.class),
            this.auth
        );

        if (response.statusCode() < 200 || response.statusCode() > 299) {
            throw new ApiException(response.body().toString());
        }

        return Rson.DEFAULT.fromJson(response.body(), FourthwallWebhook.class);
    }

}
