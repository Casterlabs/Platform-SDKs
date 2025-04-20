package co.casterlabs.sdk.kick.types;

import java.util.List;

import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.sdk.kick.webhook.KickWebhookEvent;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class KickCreatedWebhook {
    public static final TypeToken<KickCreatedWebhook[]> ARRAY_TYPE = TypeToken.of(KickCreatedWebhook[].class);
    public static final TypeToken<List<KickCreatedWebhook>> LIST_TYPE = new TypeToken<List<KickCreatedWebhook>>() {
    };

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
