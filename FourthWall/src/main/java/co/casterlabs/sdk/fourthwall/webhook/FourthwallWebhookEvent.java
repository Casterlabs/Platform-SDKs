package co.casterlabs.sdk.fourthwall.webhook;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public abstract class FourthwallWebhookEvent<T> {
    public final String id = null;
    public final String webhookId = null;
    public final String shopId = null;
    public final FourthwallWebhookType type = null;
    public final String apiVersion = null;
    public final Instant createdAt = null;
    public final Boolean testMode = null;

    public abstract T data();

}
