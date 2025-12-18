package co.casterlabs.sdk.kick.webhook.v1.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.sdk.kick.webhook.KickWebhookEvent;

@JsonClass(exposeAll = true)
public class KickWebhookLivestreamStatusUpdatedV1 implements KickWebhookEvent {

    public final KickWebhookUserV1 broadcaster = null;

    @JsonField("is_live")
    public final Boolean id = null;

    public final String title = null;

    @JsonField("started_at")
    public final Instant startedAt = null;

    @JsonField("ended_at")
    public final Instant endedAt = null;

    @Override
    public Type type() {
        return Type.LIVESTREAM_STATUS_UPDATED_V1;
    }

}
