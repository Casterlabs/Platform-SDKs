package co.casterlabs.sdk.kick.webhook.v1.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.sdk.kick.webhook.KickWebhookEvent;

@JsonClass(exposeAll = true)
public class KickWebhookChannelRewardRedemptionUpdatedV1 implements KickWebhookEvent {

    public final KickWebhookUserV1 broadcaster = null;

    public final KickWebhookUserV1 redeemer = null;

    public final String id = null;

    @JsonField("redeemed_at")
    public final Instant redeemedAt = null;

    @JsonField("user_input")
    public final String userInput = null;

    /**
     * pending, accepted, rejected
     */
    public final String status = null;

    public final Reward reward = null;

    @JsonClass(exposeAll = true)
    public static class Reward {

        public final String id = null;

        public final String title = null;

        public final Integer cost = null;

        public final String description = null;

    }

    @Override
    public Type type() {
        return Type.CHANNEL_REWARD_REDEMPTION_UPDATED_V1;
    }

}
