package co.casterlabs.sdk.youtube.types;

import java.lang.reflect.Field;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveBroadcastStatus {
    public final @JsonExclude LifeCycleStatus lifeCycleStatus = null;
    public final @JsonExclude PrivacyStatus privacyStatus = null;

    public final Boolean madeForKids = null;
    public final Boolean selfDeclaredMadeForKids = null;

    @JsonDeserializationMethod("lifeCycleStatus")
    private void $deserialize_lifeCycleStatus(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        String converted = e.getAsString()
            .replaceAll("([A-Z])", "_$1") // liveStarting -> live_Starting
            .toUpperCase(); // live_Starting -> LIVE_STARTING

        LifeCycleStatus status = LifeCycleStatus.valueOf(converted);

        Field f = YoutubeLiveBroadcastStatus.class.getField("lifeCycleStatus");
        f.setAccessible(true);
        f.set(this, status);
    }

    @JsonDeserializationMethod("privacyStatus")
    private void $deserialize_privacyStatus(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        String converted = e.getAsString()
            .toUpperCase(); // public -> PUBLIC

        PrivacyStatus status = PrivacyStatus.valueOf(converted);

        Field f = YoutubeLiveBroadcastStatus.class.getField("privacyStatus");
        f.setAccessible(true);
        f.set(this, status);
    }

    public static enum PrivacyStatus {
        PRIVATE,
        PUBLIC,
        UNLISTED;
    }

    public static enum LifeCycleStatus {
        COMPLETE,
        CREATED,
        LIVE,
        LIVE_STARTING,
        READY,
        REVOKED,
        TEST_STARTING,
        TESTING
    }

}
