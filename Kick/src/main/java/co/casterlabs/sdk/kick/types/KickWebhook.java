package co.casterlabs.sdk.kick.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.sdk.kick.webhook.KickWebhookEvent;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class KickWebhook {
    public final String id = null;
    public final String event = null;
    public final Integer version = null;
    public final String method = null;

    @JsonField("app_id")
    public final String appId = null;

    @JsonField("broadcaster_user_id")
    public final Integer broadcasterUserId = null;

    @JsonField("updated_at")
    public final Instant updatedAt = null;

    @ToString.Include
    public KickWebhookEvent.Type type() {
        return KickWebhookEvent.Type.get(this.event, this.version);
    }

}
