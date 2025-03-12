package co.casterlabs.sdk.kick.unsupported.realtime.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class UnsupportedKickChatReplyMeta {
    @JsonField("original_sender")
    private OriginalSender originalSender;

    @JsonField("original_message")
    private OriginalMessage originalMessage;

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class OriginalSender {
        private long id;
        private String username;
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class OriginalMessage {
        private String id;
        private String content;
    }

}
