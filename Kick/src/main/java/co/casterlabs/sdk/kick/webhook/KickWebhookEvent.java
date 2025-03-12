package co.casterlabs.sdk.kick.webhook;

import co.casterlabs.sdk.kick.webhook.v1.types.KickWebhookChatMessageV1;
import co.casterlabs.sdk.kick.webhook.v1.types.KickWebhookChannelFollowV1;
import co.casterlabs.sdk.kick.webhook.v1.types.KickWebhookLivestreamStatusUpdatedV1;
import co.casterlabs.sdk.kick.webhook.v1.types.KickWebhookSubscriptionCreatedV1;
import co.casterlabs.sdk.kick.webhook.v1.types.KickWebhookSubscriptionGiftsV1;
import co.casterlabs.sdk.kick.webhook.v1.types.KickWebhookSubscriptionRenewalV1;
import lombok.AllArgsConstructor;

public interface KickWebhookEvent {

    public Type type();

    @AllArgsConstructor
    public static enum Type {
        // @formatter:off
                        CHAT_MESSAGE_V1 ("chat.message.sent",            1, KickWebhookChatMessageV1.class),
                      CHANNEL_FOLLOW_V1 ("channel.followed",             1, KickWebhookChannelFollowV1.class),
        CHANNEL_SUBSCRIPTION_RENEWAL_V1 ("channel.subscription.renewal", 1, KickWebhookSubscriptionRenewalV1.class),
          CHANNEL_SUBSCRIPTION_GIFTS_V1 ("channel.subscription.gifts",   1, KickWebhookSubscriptionGiftsV1.class),
        CHANNEL_SUBSCRIPTION_CREATED_V1 ("channel.subscription.new",     1, KickWebhookSubscriptionCreatedV1.class),
           LIVESTREAM_STATUS_UPDATED_V1 ("livestream.status.updated",    1, KickWebhookLivestreamStatusUpdatedV1.class),
        // @formatter:on
        ;

        public final String type;
        public final int version;
        public final Class<? extends KickWebhookEvent> clazz;

        public static Type get(String type, int version) {
            for (Type t : Type.values()) {
                if (t.type.equals(type) && t.version == version) {
                    return t;
                }
            }
            return null;
        }

    }

}
