package co.casterlabs.sdk.fourthwall.webhook.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.sdk.fourthwall.platform.types.FourthwallAmount;
import co.casterlabs.sdk.fourthwall.webhook.FourthwallWebhookEvent;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class FourthwallWebhookSubscriptionPurchased extends FourthwallWebhookEvent<FourthwallWebhookSubscriptionPurchased.Data> {
    private final Data data = null;

    @Override
    public Data data() {
        return this.data;
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class Data {
        public final String id = null;
        public final String email = null;
        public final String nickname = null;

        public final Instant createdAt = null;

        public final Subscription subscription = null;
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class Subscription {
        public final String type = null;
        public final Variant variant = null;
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class Variant {
        public final String id = null;
        public final String tierId = null;
        public final String offerId = null;
        public final Interval interval = null;
        public final FourthwallAmount amount = null;
    }

    public static enum Interval {
        MONTHLY,
        ANNUAL,
    }

}
