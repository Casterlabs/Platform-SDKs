package co.casterlabs.sdk.kick.realtime.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class KickReactionEvent {

    @JsonField("message_id")
    private String messageId;

    private String reaction;

}
