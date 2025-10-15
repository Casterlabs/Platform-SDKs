package co.casterlabs.sdk.youtube.types;

import java.lang.reflect.Field;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveStreamCdn {
    public final @JsonExclude IngestionType ingestionType = null;

    public final YoutubeLiveStreamCdnIngestionInfo ingestionInfo = null;

    @JsonDeserializationMethod("ingestionType")
    private void $deserialize_ingestionType(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        String converted = e.getAsString()
            .replaceAll("([A-Z])", "_$1") // liveStarting -> live_Starting
            .toUpperCase(); // live_Starting -> LIVE_STARTING

        IngestionType status = IngestionType.valueOf(converted);

        Field f = YoutubeLiveBroadcastStatus.class.getField("ingestionType");
        f.setAccessible(true);
        f.set(this, status);
    }

    public static enum IngestionType {
        DASH,
        HLS,
        RTMP
    }

}
