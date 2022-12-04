package co.casterlabs.dliveapijava.realtime.events;

import java.time.Instant;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class DliveChatGift {
    // https://dev.dlive.tv/schema/chatgift.doc.html

    private String id;
    private Instant createdAt;
    private DliveChatSender sender;
    private DliveChatDonationType gift;
    private int amount;
    private @Nullable String message;

}
