package co.casterlabs.sdk.dlive.realtime.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class _DliveChatDelete {
    // https://dev.dlive.tv/schema/chatdelete.doc.html

    public final String[] ids = null;

}
