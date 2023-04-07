package co.casterlabs.sdk.kick.realtime.types;

import java.util.LinkedList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class KickChatUser {

    private long id;

    private String username;

    @JsonField("profile_thumb")
    private String profilePicture;

    private @Nullable String role;

    @JsonField("verified")
    private boolean isVerified;

    @JsonField("is_subscribed")
    private boolean isSubscribed;

    @JsonField("is_founder")
    private boolean isFounder;

//    @JsonField("isSuperAdmin") // mispelled
    private boolean isSuperAdmin;

    @JsonField("follower_badges")
    private List<String> followerBadges;

    /**
     * @return a list of URLs with SVG images.
     */
    public List<String> getBadgeUrls() {
        List<String> badges = new LinkedList<>();

        // TODO Keep this in-sync with the badges in the GitHub repo.

        // Role
        if (this.role != null) {
            switch (this.role) {
                case "Channel Host":
                    badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Roles/Channel%20Host.svg");
                    break;
                case "Moderator":
                    badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Roles/Moderator.svg");
                    break;
            }
        }
        if (this.followerBadges.contains("OG")) {
            badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Follower%20Badges/OG.svg");
        }
        if (this.followerBadges.contains("VIP")) {
            badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Follower%20Badges/VIP.svg");
        }

        // Follower Badges
        if (this.followerBadges.contains("OG")) {
            badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Follower%20Badges/OG.svg");
        }
        if (this.followerBadges.contains("VIP")) {
            badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Follower%20Badges/VIP.svg");
        }

        // Misc.
        if (this.isSubscribed) {
            badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Other/is_subscribed.svg");
        }
        if (this.isVerified) {
            badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Other/verified.svg");
        }

        return badges;
    }

}
