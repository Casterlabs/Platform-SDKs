package co.casterlabs.sdk.x.activity.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class Xv2BroadcastChatRemoveActivityStreamPayload {

    @JsonField("broadcast_id")
    public final String broadcastId = null;

    @JsonField("message_id")
    public final String messageId = null;

}
