package co.casterlabs.sdk.youtube.types.livechat.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveMessageDeletedEvent extends YoutubeLiveEvent {
    public final String deletedMessageId = null;

}
