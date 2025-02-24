package co.casterlabs.sdk.youtube.types;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Map;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveBroadcastData {
    public final String id = null;
    public final LiveBroadcastSnippet snippet = null;
    public final LiveBroadcastStatus status = null;

    public String videoUrl() {
        return String.format("https://youtu.be/%s", this.id);
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class LiveBroadcastStatus {
        public final @JsonExclude LifeCycleStatus lifeCycleStatus = null;
        public final @JsonExclude PrivacyStatus privacyStatus = null;

        @JsonDeserializationMethod("lifeCycleStatus")
        private void $deserialize_lifeCycleStatus(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
            String converted = e.getAsString()
                .replaceAll("([A-Z])", "_$1") // liveStarting -> live_Starting
                .toUpperCase(); // live_Starting -> LIVE_STARTING

            LifeCycleStatus status = LifeCycleStatus.valueOf(converted);

            Field f = LiveBroadcastStatus.class.getField("lifeCycleStatus");
            f.setAccessible(true);
            f.set(this, status);
        }

        @JsonDeserializationMethod("privacyStatus")
        private void $deserialize_privacyStatus(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
            String converted = e.getAsString()
                .toUpperCase(); // public -> PUBLIC

            PrivacyStatus status = PrivacyStatus.valueOf(converted);

            Field f = LiveBroadcastStatus.class.getField("privacyStatus");
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

    @ToString
    @JsonClass(exposeAll = true)
    public static class LiveBroadcastSnippet {
        public final String id = null;
        public final String title = null;
        public final String channelId = null;
        public final String description = null;
        public final Instant publishedAt = null;
        public final Instant scheduledStartTime = null;
        public final Boolean isDefaultBroadcast = null;
        public final String liveChatId = null;

//        @JsonExclude
        public final Map<String, YoutubeThumbnail> thumbnails = null;

//        @JsonDeserializationMethod("thumbnails")
//        private void $deserialize_thumbnails(JsonElement e) throws JsonValidationException, JsonParseException {
//            Map<String, YoutubeThumbnail> thumbs = new HashMap<>();
//
//            for (Map.Entry<String, JsonElement> entry : e.getAsObject().entrySet()) {
//                thumbs.put(
//                    entry.getKey(),
//                    Rson.DEFAULT.fromJson(entry.getValue(), YoutubeThumbnail.class)
//                );
//            }
//
//            this.thumbnails = Collections.unmodifiableMap(thumbs);
//        }

        public String videoUrl() {
            return String.format("https://youtu.be/%s", this.id);
        }

    }

}
