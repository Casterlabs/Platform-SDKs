package co.casterlabs.thetaapijava.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class ThetaUser {
    private String id;
    private String username;

    @JsonField("creation_timestamp")
    private long creationTimestamp;

    @JsonField("avatar_url")
    private String avatarUrl;

    private int subscriberCount;

    @JsonDeserializationMethod("subscriber_count")
    private void $deserialize_subscriberCount(JsonElement e) {
        if (e.isJsonNumber()) {
            this.subscriberCount = e.getAsNumber().intValue();
        }
        // Theta gives NULL for channels without subscriptions enabled.
    }

}
