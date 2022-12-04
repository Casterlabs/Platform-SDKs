package co.casterlabs.dliveapijava.realtime.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class DliveChatSender {
    private String avatar;
    private String displayname;
    private String username;

}
