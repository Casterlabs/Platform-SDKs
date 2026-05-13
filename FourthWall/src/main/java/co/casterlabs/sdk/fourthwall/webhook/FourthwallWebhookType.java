package co.casterlabs.sdk.fourthwall.webhook;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import co.casterlabs.sdk.fourthwall.webhook.types.FourthwallWebhookOrderPlaced;
import co.casterlabs.sdk.fourthwall.webhook.types.FourthwallWebhookDonation;
import co.casterlabs.sdk.fourthwall.webhook.types.FourthwallWebhookSubscriptionPurchased;
import lombok.AllArgsConstructor;

// https://docs.fourthwall.com/api-reference/platform/webhooks/create-webhook#body-allowed-types
@AllArgsConstructor
public enum FourthwallWebhookType {
    ORDER_PLACED(FourthwallWebhookOrderPlaced.class),
    ORDER_UPDATED(null),
    GIFT_PURCHASE(null),
    PRODUCT_CREATED(null),
    PRODUCT_UPDATED(null),
    DONATION(FourthwallWebhookDonation.class),
    SUBSCRIPTION_PURCHASED(FourthwallWebhookSubscriptionPurchased.class),
    SUBSCRIPTION_EXPIRED(null),
    SUBSCRIPTION_CHANGED(null),
    NEWSLETTER_SUBSCRIBED(null),
    THANK_YOU_SENT(null),
    GIFT_DRAW_STARTED(null),
    GIFT_DRAW_ENDED(null),
    PROMOTION_CREATED(null),
    PROMOTION_UPDATED(null),
    PROMOTION_STATUS_CHANGED(null),
    PLATFORM_APP_DISCONNECTED(null),
    MEMBERSHIP_POST_UPSERTED(null),
    MEMBERSHIP_SERIES_UPSERTED(null),
    MEMBERSHIP_SERIES_DELETED(null),
    MEMBERSHIP_TAG_CREATED(null),
    MEMBERSHIP_TAG_UPDATED(null),
    MEMBERSHIP_TAG_DELETED(null),
    MEMBERSHIP_TIER_UPSERTED(null),
    MEMBERSHIP_TIER_DELETED(null),
    COLLECTION_UPDATED(null),
    CART_ABANDONED_1H(null),
    CART_ABANDONED_24H(null),
    CART_ABANDONED_72H(null),

    ;

    private final Class<?> clazz;

    public static FourthwallWebhookEvent<?> from(JsonObject json) throws JsonValidationException, JsonParseException {
        String type = json.getString("type");
        for (FourthwallWebhookType wh : FourthwallWebhookType.values()) {
            if (!wh.name().equals(type)) continue;

            if (wh.clazz == null) {
                throw new UnsupportedOperationException("This webhook type is not supported yet.");
            }

            return (FourthwallWebhookEvent<?>) Rson.DEFAULT.fromJson(json, wh.clazz);
        }

        throw new IllegalArgumentException("Unknown webhook type: " + type);
    }

}
