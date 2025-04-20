package co.casterlabs.sdk.kick.webhook.v1.types;

import org.jetbrains.annotations.Nullable;

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

    /**
     * @apiNote null if:
     *          <ul>
     *          <li>the user type is for a broadcaster.</li>
     *          <li>the user is anonymous.</li>
     *          <li><i>any undocumented edge-case.</i></li>
     *          </ul>
     */
    public final @Nullable Identity identity = null;

    @JsonClass(exposeAll = true)
    public class Identity {

        @JsonField("username_color")
        public final String color = null;

        @JsonClass(exposeAll = true)
        public static class Badge {
            public final String text = null;
            public final String type = null;
        }

    }

}
