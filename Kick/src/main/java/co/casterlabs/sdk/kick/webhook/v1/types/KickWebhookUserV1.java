package co.casterlabs.sdk.kick.webhook.v1.types;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

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

            public String getImageUrl() {
                switch (this.type) {
                    case "broadcaster":
                        return "https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/broadcaster.svg";
                    case "moderator":
                        return "https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/moderator.svg";
                    case "vip":
                        return "https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/vip.svg";
                    case "og":
                        return "https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/og.svg";
                    case "verified":
                        return "https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/verified.svg";
                    case "founder":
                        return "https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/founder.svg";
                    case "staff":
                        return "https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/staff.svg";
                    case "subscriber":
                        return "https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/subscriber.svg";

                    case "sub_gifter":
                        // TODO detect if they are gifter level 1 or 2.
                        return "https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/sub_gifter_1.svg";

                    default:
                        FastLogger.logStatic(LogLevel.WARNING, "Unknown badge type: %s ('%s')", this.type, this.text);
                        return null;
                }
            }

        }

    }

}
