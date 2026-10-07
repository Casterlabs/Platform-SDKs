package co.casterlabs.sdk.x.activity.types;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class Xv2BroadcastChatActivityPayload {

    @JsonField("broadcast_id")
    public final String broadcastId = null;

    @JsonField("message_id")
    public final String messageId = null;

    @JsonField("reply_to")
    public final @Nullable String replyTo = null;

    public final String message = null;

    @JsonField("is_subscriber")
    public final Boolean isSubscriber = null;

    @JsonField("is_moderator")
    public final Boolean isModerator = null;

    public final Author author = null;

    @ToString
    @JsonClass(exposeAll = true)
    public static class Author {
        public final Xv2ActivityUser data = null;
    }

}
