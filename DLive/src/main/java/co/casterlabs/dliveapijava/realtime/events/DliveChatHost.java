package co.casterlabs.dliveapijava.realtime.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class DliveChatHost {
    // https://dev.dlive.tv/schema/chatfollow.doc.html

    private DliveChatSender sender;
    
    @JsonField("viewer")
    private int viewers;

}
