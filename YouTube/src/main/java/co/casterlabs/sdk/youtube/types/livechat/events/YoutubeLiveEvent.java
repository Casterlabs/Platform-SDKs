package co.casterlabs.sdk.youtube.types.livechat.events;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Calendar;

import javax.xml.bind.DatatypeConverter;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public abstract class YoutubeLiveEvent {
    public final String liveChatId = null;
    public final String authorChannelId = null;
    public final Boolean hasDisplayContent = null;
    public final String displayMessage = null;

    public final @JsonExclude YoutubeLiveEventType type = null;
    public final @JsonExclude Instant publishedAt = null;

    @JsonDeserializationMethod("publishedAt")
    private void $deserialize_publishedAt(JsonElement element) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // ISO 8601 format
        Calendar cal = DatatypeConverter.parseDateTime(element.getAsString());
        Instant publishedAt = cal.toInstant();

        Field f = YoutubeLiveEvent.class.getField("publishedAt");
        f.setAccessible(true);
        f.set(this, publishedAt);
    }

    @JsonDeserializationMethod("type")
    private void $deserialize_type(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        YoutubeLiveEventType type = YoutubeLiveEventType.parseType(e.getAsString());

        Field f = YoutubeLiveEvent.class.getField("type");
        f.setAccessible(true);
        f.set(this, type);
    }

}
