package co.casterlabs.sdk.twitch.helix.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class HelixFollower extends HelixSimpleUser {

    @JsonField("followed_at")
    public final Instant followedAt = null;

}
