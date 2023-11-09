package co.casterlabs.sdk.kick.types;

import java.time.Instant;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.Getter;
import lombok.ToString;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

@ToString
@JsonClass(exposeAll = true)
public class KickActivityFeedEvent {
    @JsonField("event_type")
    private String eventType;

    @JsonField("event_data")
    private JsonObject eventData;

    @Getter
    @JsonExclude
    private Instant createdAt;

    @JsonDeserializationMethod("created_at")
    private void $deserialize_created_at(JsonElement e) {
        this.createdAt = Instant.parse(e.getAsString());
    }

    public void getDataAsFollowerAdded() {

    }

    public @Nullable AFType getEventType() {
        switch (this.eventType) {
            case "FollowerAdded":
                return AFType.FOLLOWER_ADDED;

            case "SubscriptionRenewed":
                return AFType.SUBSCRIPTION_RENEWED;

            default:
                FastLogger.logStatic(LogLevel.WARNING, "Unrecognized Activity Feed event type:\n%s: %s", this.eventType, this.eventData);
                return null;
        }
    }

    public static enum AFType {
        FOLLOWER_ADDED,
        SUBSCRIPTION_RENEWED,
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class AFUser {
        private int id;
        private String slug;
        private String user;
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class AFFollowerAddedEvent {
        private AFUser channel;
        private @JsonField("user") AFUser follower;
    }

}
