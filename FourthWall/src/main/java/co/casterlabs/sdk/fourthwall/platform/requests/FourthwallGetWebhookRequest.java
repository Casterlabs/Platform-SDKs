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
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class FourthwallGetWebhookRequest extends AuthenticatedWebRequest<FourthwallWebhook, FourthwallAuth> {
    private @NonNull String webhookId;

    public FourthwallGetWebhookRequest(@NonNull FourthwallAuth auth) {
        super(auth);
    }

    @Override
    protected FourthwallWebhook execute() throws ApiException, ApiAuthException, IOException {
        assert this.webhookId != null : "You must specify the Webhook ID to check.";

        HttpResponse<JsonObject> response = _RequestHelper.GET("https://api.fourthwall.com/open-api/v1.0/webhooks/" + this.webhookId, this.auth);
        return Rson.DEFAULT.fromJson(response.body(), FourthwallWebhook.class);
    }

}
