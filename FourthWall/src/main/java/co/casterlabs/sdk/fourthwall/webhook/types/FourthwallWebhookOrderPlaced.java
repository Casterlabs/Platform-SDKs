package co.casterlabs.sdk.fourthwall.webhook.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.fourthwall.platform.types.FourthwallAmount;
import co.casterlabs.sdk.fourthwall.platform.types.FourthwallGiftCardUse;
import co.casterlabs.sdk.fourthwall.platform.types.FourthwallProductOffer;
import co.casterlabs.sdk.fourthwall.platform.types.FourthwallTrackingParams;
import co.casterlabs.sdk.fourthwall.webhook.FourthwallWebhookEvent;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class FourthwallWebhookOrderPlaced extends FourthwallWebhookEvent<FourthwallWebhookOrderPlaced.Data> {
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
        public final String friendlyId = null;
        public final String checkoutId = null;
        public final String status = null;
        public final String email = null;
        public final Boolean emailMarketingOptIn = null;
        public final String message = null;

        public final Instant createdAt = null;
        public final Instant updatedAt = null;

        public final String promotionId = null;
        public final String username = null;

        public final FourthwallTrackingParams trackingParams = null;
        public final FourthwallProductOffer[] offers = null;
        public final Amounts amounts = null;
        public final Source source = null;

        public final JsonObject metadata = null;
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class Amounts {
        public final FourthwallAmount subtotal = null;
        public final FourthwallAmount shipping = null;
        public final FourthwallAmount tax = null;
        public final FourthwallAmount donation = null;
        public final FourthwallAmount discount = null;
        public final FourthwallGiftCardUse[] giftCards = null;
        public final FourthwallAmount total = null;
    }

    @ToString
    @JsonClass(exposeAll = true)
    public class Source {
        public final String type = null;
        public final String giftId = null;

    }

}
