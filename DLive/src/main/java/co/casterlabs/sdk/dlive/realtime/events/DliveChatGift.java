package co.casterlabs.sdk.dlive.realtime.events;

import java.time.Instant;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class DliveChatGift {
    // https://dev.dlive.tv/schema/chatgift.doc.html

    private String id;
    private JsonElement createdAt;
    private DliveChatSender sender;
    private DliveChatDonationType gift;
    private int amount;
    private @Nullable String message;

    public Instant getCreatedAt() {
        long value;
        if (this.createdAt.isJsonString()) {
            value = Long.parseLong(this.createdAt.getAsString());
        } else {
            value = this.createdAt.getAsNumber().longValue();
        }
        return Instant.ofEpochMilli(value);
    }

}
