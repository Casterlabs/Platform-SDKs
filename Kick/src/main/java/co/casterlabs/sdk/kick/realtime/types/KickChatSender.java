package co.casterlabs.sdk.kick.realtime.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class KickChatSender {
    private long id;

    private String username;

    private String slug;

    private Identity identity;

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class Identity {
        private String color;
//        private List<Badge> badges;
//
//        @Getter
//        @ToString
//        @JsonClass(exposeAll = true)
//        public static class Badge {
//            private String type;
//            private String text;
//        }
    }

    /**
     * @return a list of URLs with SVG images.
     */
//    public List<String> getBadgeUrls() {
//        List<String> badges = new LinkedList<>();
//
//        // TODO Keep this in-sync with the badges in the GitHub repo.
//
//        // Role
//        if (this.role != null) {
//            switch (this.role) {
//                case "Channel Host":
//                    badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Roles/Channel%20Host.svg");
//                    break;
//                case "Moderator":
//                    badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Roles/Moderator.svg");
//                    break;
//            }
//        }
//        if (this.followerBadges.contains("OG")) {
//            badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Follower%20Badges/OG.svg");
//        }
//        if (this.followerBadges.contains("VIP")) {
//            badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Follower%20Badges/VIP.svg");
//        }
//
//        // Follower Badges
//        if (this.followerBadges.contains("OG")) {
//            badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Follower%20Badges/OG.svg");
//        }
//        if (this.followerBadges.contains("VIP")) {
//            badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Follower%20Badges/VIP.svg");
//        }
//
//        // Misc.
//        if (this.isSubscribed) {
//            badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Other/is_subscribed.svg");
//        }
//        if (this.isVerified) {
//            badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Other/verified.svg");
//        }
//        if (this.isSuperAdmin) {
//            badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Other/isSuperAdmin.svg");
//        }
//        if (this.isFounder) {
//            badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Other/is_founder.svg");
//        }
//        if (this.username.equalsIgnoreCase("TrainwrecksTV")) {
//            badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Other/TrainwrecksTV.svg");
//        }
//
//        return badges;
//    }

}
