package co.casterlabs.sdk.kick.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;

@JsonClass(exposeAll = true)
public class KickPostedChatMessage {

    @JsonField("is_sent")
    public final Boolean sent = null;

    @JsonField("message_id")
    public final String messageId = null;

}
