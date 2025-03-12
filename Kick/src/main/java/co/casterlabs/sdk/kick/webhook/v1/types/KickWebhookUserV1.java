package co.casterlabs.sdk.kick.webhook.v1.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;

@JsonClass(exposeAll = true)
public class KickWebhookUserV1 {

    public final String username = null;

    @JsonField("is_anonymous")
    public final Boolean isAnonymous = null;

    @JsonField("user_id")
    public final String id = null;

    @JsonField("is_verified")
    public final Boolean isVerified = null;

    @JsonField("channel_slug")
    public final String slug = null;

    @JsonField("profile_picture")
    public final String profilePictureUrl = null;

}
