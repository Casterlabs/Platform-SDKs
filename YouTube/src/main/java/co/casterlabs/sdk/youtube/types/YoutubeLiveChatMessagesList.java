package co.casterlabs.sdk.youtube.types;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import co.casterlabs.sdk.youtube.types.livechat.YoutubeLiveChatEvent;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveChatMessagesList {
    public final boolean isHistorical = false;

    public final String nextPageToken = null;

    public final Long pollingIntervalMillis = null;

    public final List<YoutubeLiveChatEvent> events = null;

    @JsonDeserializationMethod("items")
    private void $deserialize_items(JsonElement itemsElement) throws JsonValidationException, JsonParseException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        JsonArray items = itemsElement.getAsArray();

        List<YoutubeLiveChatEvent> events;
        if (items.isEmpty()) {
            events = Collections.emptyList();
        } else {
            events = items.toList()
                .stream()
                .map((e) -> e.getAsObject())
                .map((item) -> {
                    // Inject the historical tag.
                    return item.put("isHistorical", this.isHistorical);
                })
                .map((item) -> {
                    try {
                        return Rson.DEFAULT.fromJson(item, YoutubeLiveChatEvent.class);
                    } catch (JsonParseException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.toList());
        }

        Field f = YoutubeLiveChatMessagesList.class.getField("events");
        f.setAccessible(true);
        f.set(this, events);
    }

    public boolean isEmpty() {
        return this.events.isEmpty();
    }

}
