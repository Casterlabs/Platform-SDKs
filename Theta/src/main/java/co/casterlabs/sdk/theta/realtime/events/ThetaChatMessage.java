package co.casterlabs.sdk.theta.realtime.events;

import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class ThetaChatMessage {
    @JsonField("message_id")
    protected String messageId;

    protected String text;
    protected long timestamp;

    protected ThetaChatUser user;
    protected List<ThetaChatEmote> emotes;

}
