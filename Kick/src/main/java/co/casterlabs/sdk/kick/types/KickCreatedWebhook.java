package co.casterlabs.sdk.kick.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.sdk.kick.webhook.KickWebhookEvent;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class KickCreatedWebhook {
    public final String error = null;
    public final Integer version = null;

    @JsonField("name")
    public final String event = null;

    @JsonField("subscription_id")
    public final String id = null;

    @ToString.Include
    public KickWebhookEvent.Type type() {
        return KickWebhookEvent.Type.get(this.event, this.version);
    }

}
