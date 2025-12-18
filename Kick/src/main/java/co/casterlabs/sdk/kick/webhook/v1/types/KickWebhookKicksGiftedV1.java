package co.casterlabs.sdk.kick.webhook.v1.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.sdk.kick.webhook.KickWebhookEvent;

@JsonClass(exposeAll = true)
public class KickWebhookKicksGiftedV1 implements KickWebhookEvent {

    public final KickWebhookUserV1 broadcaster = null;

    public final KickWebhookUserV1 sender = null;

    @JsonField("created_at")
    public final Instant createdAt = null;

    public final Gift gift = null;

    @JsonClass(exposeAll = true)
    public static class Gift {

        public Integer amount = null;

        public String name = null;

        public String type = null;

        public String tier = null;

        public String message = null;

        @JsonField("pinned_time_seconds")
        public Integer pinnedTimeSeconds = null;

    }

    @Override
    public Type type() {
        return Type.KICKS_GIFTED_V1;
    }

}
