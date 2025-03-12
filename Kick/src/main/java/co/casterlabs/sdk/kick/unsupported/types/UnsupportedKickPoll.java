package co.casterlabs.sdk.kick.unsupported.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.sdk.kick.unsupported.realtime.types.UnsupportedKickPollUpdateEvent;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@JsonClass(exposeAll = true)
public class UnsupportedKickPoll extends UnsupportedKickPollUpdateEvent {
    // Everything else is specified in KickPollUpdateEvent.

    private @JsonExclude Instant createdAt;

    @JsonDeserializationMethod("created_at")
    private void $deserialize_created_at(JsonElement e) {
        this.createdAt = Instant.parse(e.getAsString());
    }

}
