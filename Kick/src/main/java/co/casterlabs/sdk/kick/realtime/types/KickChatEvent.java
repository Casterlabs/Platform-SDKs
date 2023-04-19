package co.casterlabs.sdk.kick.realtime.types;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class KickChatEvent {
    private String id;

    private String content;

    private KickChatSender sender;

    @JsonField("metadata")
    private @Nullable KickChatReplyMeta replyMetadata;

}
