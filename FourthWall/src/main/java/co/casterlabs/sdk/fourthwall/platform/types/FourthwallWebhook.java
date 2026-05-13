package co.casterlabs.sdk.fourthwall.platform.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.sdk.fourthwall.webhook.FourthwallWebhookType;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class FourthwallWebhook {
    public final String id = null;
    public final String url = null;
    public final FourthwallWebhookType[] allowedTypes = null;
    public final String apiVersion = null;

}
