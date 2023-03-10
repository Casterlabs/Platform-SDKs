package co.casterlabs.sdk.kick.realtime.types;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class KickChatMessage {

    private String id;

    private String message;

    @JsonField("replied_to")
    private @Nullable KickChatReplyTarget replyingTo;

    @JsonField("created_at")
    private long createdAt;

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class KickChatReplyTarget {
        private String id;
        private String message;
        private String username;

    }

}
