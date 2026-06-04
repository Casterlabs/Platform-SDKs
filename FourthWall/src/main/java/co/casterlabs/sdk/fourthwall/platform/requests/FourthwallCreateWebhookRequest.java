package co.casterlabs.sdk.fourthwall.platform.requests;

import java.io.IOException;
import java.net.http.HttpResponse;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
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

        HttpResponse<JsonObject> response = _RequestHelper.POST("https://api.fourthwall.com/open-api/v1.0/webhooks", body, this.auth);
        return Rson.DEFAULT.fromJson(response.body(), FourthwallWebhook.class);
    }

}
