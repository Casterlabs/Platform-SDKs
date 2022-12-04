package co.casterlabs.youtubeapijava.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import co.casterlabs.youtubeapijava.YoutubeApi;
import co.casterlabs.youtubeapijava.types.livechat.YoutubeLiveChatEvent;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveChatMessagesList {
    private boolean isHistorical;

    private String nextPageToken;

    private long pollingIntervalMillis;

    private List<YoutubeLiveChatEvent> events;

    @JsonDeserializationMethod("items")
    private void $deserialize_items(JsonElement itemsElement) throws JsonValidationException, JsonParseException {
        JsonArray items = itemsElement.getAsArray();

        List<YoutubeLiveChatEvent> events = new ArrayList<>(items.size());

        for (JsonElement item : items) {
            item.getAsObject().put("isHistorical", this.isHistorical);
            events.add(YoutubeApi.RSON.fromJson(item, YoutubeLiveChatEvent.class));
        }

        this.events = Collections.unmodifiableList(events);
    }

    public boolean isEmpty() {
        return this.events.isEmpty();
    }

}
