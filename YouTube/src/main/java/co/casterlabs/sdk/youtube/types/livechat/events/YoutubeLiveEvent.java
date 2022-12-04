package co.casterlabs.sdk.youtube.types.livechat.events;

import java.time.Instant;
import java.util.Calendar;

import javax.xml.bind.DatatypeConverter;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public abstract class YoutubeLiveEvent {
    private @JsonExclude YoutubeLiveEventType type;
    private String liveChatId;
    private String authorChannelId;
    private @JsonExclude Instant publishedAt;
    private boolean hasDisplayContent;
    private String displayMessage;

    @JsonDeserializationMethod("publishedAt")
    private void $deserialize_publishedAt(JsonElement element) {
        // ISO 8601 format
        Calendar cal = DatatypeConverter.parseDateTime(element.getAsString());
        this.publishedAt = cal.toInstant();
    }

    @JsonDeserializationMethod("type")
    private void $deserialize_type(JsonElement e) {
        this.type = YoutubeLiveEventType.parseType(e.getAsString());
    }

}
