package co.casterlabs.sdk.youtube.types.livechat;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import co.casterlabs.sdk.youtube.types.livechat.events.YoutubeLiveEvent;
import co.casterlabs.sdk.youtube.types.livechat.events.YoutubeLiveEventType;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveChatEvent {
    public final String id = null;
    public final boolean isHistorical = false;
    public final YoutubeLiveChatAuthor authorDetails = new YoutubeLiveChatAuthor();

    private @JsonExclude YoutubeLiveEvent event;

    @JsonDeserializationMethod("snippet")
    private void $deserialize_snippet(JsonElement snippetElement) throws JsonValidationException, JsonParseException {
        this.event = YoutubeLiveEventType.fromJson(snippetElement.getAsObject());
    }

    @SuppressWarnings("unchecked")
    public <T extends YoutubeLiveEvent> T event() {
        return (T) this.event;
    }

}
