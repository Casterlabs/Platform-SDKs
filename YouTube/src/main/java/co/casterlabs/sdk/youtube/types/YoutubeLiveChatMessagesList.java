package co.casterlabs.sdk.youtube.types;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        List<YoutubeLiveChatEvent> events = new ArrayList<>(items.size());

        for (JsonElement item : items) {
            item.getAsObject().put("isHistorical", this.isHistorical);
            events.add(Rson.DEFAULT.fromJson(item, YoutubeLiveChatEvent.class));
        }

        Field f = YoutubeLiveChatMessagesList.class.getField("events");
        f.setAccessible(true);
        f.set(this, Collections.unmodifiableList(events));
    }

    public boolean isEmpty() {
        return this.events.isEmpty();
    }

}
