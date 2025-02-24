package co.casterlabs.sdk.dlive.realtime.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class DliveChatHost {
    // https://dev.dlive.tv/schema/chatfollow.doc.html

    public final DliveChatSender sender = null;

    @JsonField("viewer")
    public final Integer viewers = null;

}
