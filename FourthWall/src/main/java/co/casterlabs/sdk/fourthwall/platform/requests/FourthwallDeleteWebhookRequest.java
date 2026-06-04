package co.casterlabs.sdk.fourthwall.platform.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.fourthwall.FourthwallAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class FourthwallDeleteWebhookRequest extends AuthenticatedWebRequest<Void, FourthwallAuth> {
    private @NonNull String webhookId;

    public FourthwallDeleteWebhookRequest(@NonNull FourthwallAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.webhookId != null : "You must specify the Webhook ID to check.";

        _RequestHelper.DELETE("https://api.fourthwall.com/open-api/v1.0/webhooks/" + this.webhookId, this.auth);
        return null;
    }

}
