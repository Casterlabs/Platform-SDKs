package co.casterlabs.sdk.dlive.realtime.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class DliveChatSubscription {
    // https://dev.dlive.tv/schema/chatsubscription.doc.html

    public final DliveChatSender sender = null;
    public final Integer month = null;

    public boolean isMonthly() {
        return this.month == 0;
    }

}
