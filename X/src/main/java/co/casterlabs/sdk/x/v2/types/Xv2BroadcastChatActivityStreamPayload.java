package co.casterlabs.sdk.x.v2.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class Xv2BroadcastChatActivityStreamPayload {

    @JsonField("broadcast_id")
    public final String broadcastId = null;

    @JsonField("message_id")
    public final String messageId = null;

    public final String message = null;

    public final Author author = null;

    @ToString
    @JsonClass(exposeAll = true)
    public static class Author {
        public final Xv2ActivityUser data = null;
    }

}
