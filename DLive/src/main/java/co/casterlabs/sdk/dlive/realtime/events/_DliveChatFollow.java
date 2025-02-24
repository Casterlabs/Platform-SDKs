package co.casterlabs.sdk.dlive.realtime.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class _DliveChatFollow {
    // https://dev.dlive.tv/schema/chatfollow.doc.html

    public final DliveChatSender sender = null;

}
