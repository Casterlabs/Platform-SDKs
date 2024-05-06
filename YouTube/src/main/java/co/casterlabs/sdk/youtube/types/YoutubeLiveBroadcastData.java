package co.casterlabs.sdk.youtube.types;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveBroadcastData {
    private String id;
    private LiveBroadcastSnippet snippet;
    private LiveBroadcastStatus status;

    public String getVideoUrl() {
        return String.format("https://youtu.be/%s", this.id);
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class LiveBroadcastStatus {
        private @JsonExclude LifeCycleStatus lifeCycleStatus;
        private @JsonExclude PrivacyStatus privacyStatus;

        @JsonDeserializationMethod("lifeCycleStatus")
        private void $deserialize_lifeCycleStatus(JsonElement e) {
            String converted = e.getAsString()
                .replaceAll("([A-Z])", "_$1") // liveStarting -> live_Starting
                .toUpperCase(); // live_Starting -> LIVE_STARTING

            this.lifeCycleStatus = LifeCycleStatus.valueOf(converted);
        }

        @JsonDeserializationMethod("privacyStatus")
        private void $deserialize_privacyStatus(JsonElement e) {
            String converted = e.getAsString()
                .toUpperCase(); // public -> PUBLIC

            this.privacyStatus = PrivacyStatus.valueOf(converted);
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

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class LiveBroadcastSnippet {
        private String id;

        private String title;

        private String channelId;

        private String description;

        private Instant publishedAt;

        private Instant scheduledStartTime;

        private boolean isDefaultBroadcast;

        private String liveChatId;

        @JsonExclude
        private Map<String, YoutubeThumbnail> thumbnails;

        @JsonDeserializationMethod("thumbnails")
        private void $deserialize_thumbnails(JsonElement e) throws JsonValidationException, JsonParseException {
            Map<String, YoutubeThumbnail> thumbs = new HashMap<>();

            for (Map.Entry<String, JsonElement> entry : e.getAsObject().entrySet()) {
                thumbs.put(
                    entry.getKey(),
                    Rson.DEFAULT.fromJson(entry.getValue(), YoutubeThumbnail.class)
                );
            }

            this.thumbnails = Collections.unmodifiableMap(thumbs);
        }

        public String getVideoUrl() {
            return String.format("https://youtu.be/%s", this.id);
        }

    }

}
