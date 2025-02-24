package co.casterlabs.sdk.youtube.types.livechat.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveSuperChatEvent extends YoutubeLiveEvent {
    public final Long amountMicros = null;
    public final String currency = null;
    public final String amountDisplayString = null;
    public final String userComment = null;
    public final Integer tier = null;

}
