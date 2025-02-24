package co.casterlabs.sdk.dlive.realtime.events;

import java.lang.reflect.Field;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class DliveChatGift {
    // https://dev.dlive.tv/schema/chatgift.doc.html

    public final String id = null;
    public final DliveChatSender sender = null;
    public final DliveChatDonationType gift = null;
    public final Integer amount = null;
    public final @Nullable String message = null;

    public final @JsonExclude Long createdAt = null;

    @JsonDeserializationMethod("createdAt")
    private void $deserialize_createdAt(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        long value;
        if (e.isJsonString()) {
            value = Long.parseLong(e.getAsString());
        } else {
            value = e.getAsNumber().longValue();
        }

        Field f = DliveChatGift.class.getField("createdAt");
        f.setAccessible(true);
        f.set(this, value);
    }

}
