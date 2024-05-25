package co.casterlabs.sdk.twitch.helix.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class HelixUser {
    public final String id = null;

    public final String login = null;

    @JsonField("display_name")
    public final String displayName = null;

    /**
     * Empty unless you request with a user access token with the
     * <b>user:read:email</b> scope.
     */
    public final String email = null;

    @JsonField("created_at")
    public final Instant createdAt = null;

    @JsonField("broadcaster_type")
    public final String broadcasterType = null;

    public final String description = null;

    @JsonField("profile_image_url")
    public final String profileImageUrl = null;

    @JsonField("offline_image_url")
    public final String offlineImageUrl = null;

}
