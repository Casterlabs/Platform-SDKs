package co.casterlabs.sdk.kick.webhook.v1.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.sdk.kick.webhook.KickWebhookEvent;

@JsonClass(exposeAll = true)
public class KickWebhookSubscriptionRenewalV1 implements KickWebhookEvent {

    public final KickWebhookUserV1 broadcaster = null;

    public final KickWebhookUserV1 subscriber = null;

    public final Integer duration = null;

    @JsonField("created_at")
    public final Instant createdAt = null;

    @Override
    public Type type() {
        return Type.CHANNEL_SUBSCRIPTION_RENEWAL_V1;
    }

}
