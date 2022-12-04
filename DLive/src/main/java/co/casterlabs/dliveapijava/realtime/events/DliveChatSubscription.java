package co.casterlabs.dliveapijava.realtime.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class DliveChatSubscription {
    // https://dev.dlive.tv/schema/chatsubscription.doc.html

    private DliveChatSender sender;
    private int month;

    public boolean isMonthly() {
        return this.month == 0;
    }

}
