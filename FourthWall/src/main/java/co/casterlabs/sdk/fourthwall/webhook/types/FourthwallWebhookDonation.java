package co.casterlabs.sdk.fourthwall.webhook.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.sdk.fourthwall.platform.types.FourthwallDonation;
import co.casterlabs.sdk.fourthwall.webhook.FourthwallWebhookEvent;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class FourthwallWebhookDonation extends FourthwallWebhookEvent<FourthwallDonation> {
    private final FourthwallDonation data = null;

    @Override
    public FourthwallDonation data() {
        return this.data;
    }

}
