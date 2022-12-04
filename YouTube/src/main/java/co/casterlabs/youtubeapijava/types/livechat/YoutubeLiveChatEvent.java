package co.casterlabs.youtubeapijava.types.livechat;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import co.casterlabs.youtubeapijava.types.livechat.events.YoutubeLiveEvent;
import co.casterlabs.youtubeapijava.types.livechat.events.YoutubeLiveEventType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveChatEvent {
    private String id;
    private boolean isHistorical;
    private YoutubeLiveChatAuthor authorDetails;
    private @JsonExclude YoutubeLiveEvent event;

    @JsonDeserializationMethod("snippet")
    private void $deserialize_snippet(JsonElement snippetElement) throws JsonValidationException, JsonParseException {
        this.event = YoutubeLiveEventType.fromJson(snippetElement.getAsObject());
    }

    @SuppressWarnings("unchecked")
    public <T extends YoutubeLiveEvent> T getEvent() {
        return (T) this.event;
    }

}
