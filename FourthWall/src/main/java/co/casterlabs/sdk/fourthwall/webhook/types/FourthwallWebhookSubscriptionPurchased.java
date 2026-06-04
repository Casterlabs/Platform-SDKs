package co.casterlabs.sdk.fourthwall.webhook.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.sdk.fourthwall.platform.types.FourthwallSubscription;
import co.casterlabs.sdk.fourthwall.webhook.FourthwallWebhookEvent;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class FourthwallWebhookSubscriptionPurchased extends FourthwallWebhookEvent<FourthwallSubscription> {
    private final FourthwallSubscription data = null;

    @Override
    public FourthwallSubscription data() {
        return this.data;
    }

}
