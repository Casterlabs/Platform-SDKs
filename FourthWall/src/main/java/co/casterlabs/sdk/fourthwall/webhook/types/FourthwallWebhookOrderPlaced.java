package co.casterlabs.sdk.fourthwall.webhook.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.sdk.fourthwall.platform.types.FourthwallOrder;
import co.casterlabs.sdk.fourthwall.webhook.FourthwallWebhookEvent;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class FourthwallWebhookOrderPlaced extends FourthwallWebhookEvent<FourthwallOrder> {
    private final FourthwallOrder data = null;

    @Override
    public FourthwallOrder data() {
        return this.data;
    }

}
