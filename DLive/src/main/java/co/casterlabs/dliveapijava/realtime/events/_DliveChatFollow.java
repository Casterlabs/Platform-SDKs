package co.casterlabs.dliveapijava.realtime.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class _DliveChatFollow {
    // https://dev.dlive.tv/schema/chatfollow.doc.html

    private DliveChatSender sender;

}
