package co.casterlabs.sdk.kick.unsupported.realtime.types;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class UnsupportedKickChatEvent {
    private String id;

    private String content;

    private UnsupportedKickChatSender sender;

    @JsonField("metadata")
    private @Nullable UnsupportedKickChatReplyMeta replyMetadata;

}
