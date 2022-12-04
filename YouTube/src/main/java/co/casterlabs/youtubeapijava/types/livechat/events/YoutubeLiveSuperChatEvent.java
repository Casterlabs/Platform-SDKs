package co.casterlabs.youtubeapijava.types.livechat.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveSuperChatEvent extends YoutubeLiveEvent {
    private long amountMicros;
    private String currency;
    private String amountDisplayString;
    private String userComment;
    private int tier;

}
