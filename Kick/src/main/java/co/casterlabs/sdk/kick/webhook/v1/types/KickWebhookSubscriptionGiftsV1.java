package co.casterlabs.sdk.kick.webhook.v1.types;

import java.time.Instant;
import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.sdk.kick.webhook.KickWebhookEvent;

@JsonClass(exposeAll = true)
public class KickWebhookSubscriptionGiftsV1 implements KickWebhookEvent {

    public final KickWebhookUserV1 broadcaster = null;

    public final KickWebhookUserV1 gifter = null;

    public final List<KickWebhookUserV1> giftees = null;

    @JsonField("created_at")
    public final Instant createdAt = null;

    @Override
    public Type type() {
        return Type.CHANNEL_SUBSCRIPTION_GIFTS_V1;
    }

}
