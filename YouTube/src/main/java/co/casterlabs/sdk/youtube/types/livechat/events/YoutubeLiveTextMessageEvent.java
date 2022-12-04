package co.casterlabs.sdk.youtube.types.livechat.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveTextMessageEvent extends YoutubeLiveEvent {
    private String messageText;

}
