package co.casterlabs.sdk.kick.webhook.v1.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.sdk.kick.webhook.KickWebhookEvent;

@JsonClass(exposeAll = true)
public class KickWebhookChannelFollowV1 implements KickWebhookEvent {

    public final KickWebhookUserV1 broadcaster = null;

    public final KickWebhookUserV1 follower = null;

    @Override
    public Type type() {
        return Type.CHANNEL_FOLLOW_V1;
    }

}
