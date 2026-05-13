package co.casterlabs.sdk.fourthwall.webhook.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.sdk.fourthwall.platform.types.FourthwallAmount;
import co.casterlabs.sdk.fourthwall.webhook.FourthwallWebhookEvent;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class FourthwallWebhookDonation extends FourthwallWebhookEvent<FourthwallWebhookDonation.Data> {
    private final Data data = null;

    @Override
    public Data data() {
        return this.data;
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class Data {
        public final String id = null;
        public final String shopId = null;
        public final String status = null;
        public final String email = null;
        public final String username = null;
        public final String message = null;

        public final Amounts amounts = null;

        public final Instant createdAt = null;
        public final Instant updatedAt = null;
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class Amounts {
        public final FourthwallAmount total = null;
    }

}
