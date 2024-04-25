package co.casterlabs.sdk.kick.realtime.types;

import java.util.LinkedList;
import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.sdk.kick.types.KickChannel.SubscriberBadge;
import lombok.Getter;
import lombok.ToString;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

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
        private List<Badge> badges;

    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class Badge {
        private String type;
        private String text;
        private int count = -1;
    }

    /**
     * @return a list of URLs with SVG images.
     */
    public List<String> getBadgeUrls() {
        return this.getBadgeUrls(new SubscriberBadge[0]);
    }

    /**
     * @return a list of URLs with SVG images.
     */
    public List<String> getBadgeUrls(SubscriberBadge[] channelSubscriberBadges) {
        List<String> badges = new LinkedList<>();

        for (Badge badge : this.identity.badges) {
            switch (badge.type) {
                case "broadcaster":
                    badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/broadcaster.svg");
                    break;

                case "moderator":
                    badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/moderator.svg");
                    break;

                case "vip":
                    badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/vip.svg");
                    break;

                case "og":
                    badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/og.svg");
                    break;

                case "verified":
                    badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/verified.svg");
                    break;

                case "founder":
                    badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/founder.svg");
                    break;

                case "staff":
                    badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/staff.svg");
                    break;

                case "subscriber": {
                    String url = "https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/subscriber.svg";
                    for (SubscriberBadge custom : channelSubscriberBadges) {
                        if (badge.count >= custom.getMonths()) {
                            url = custom.getImageURL();
                        }
                    }
                    badges.add(url);
                    break;
                }

                case "sub_gifter":
                    // TODO detect if they are gifter level 1 or 2.
                    badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/sub_gifter_1.svg");
                    break;

                default:
                    FastLogger.logStatic(LogLevel.WARNING, "Unknown badge type: %s ('%s')", badge.type, badge.text);
                    break;
            }
        }

        if (this.username.equalsIgnoreCase("TrainwrecksTV")) {
            badges.add("https://raw.githubusercontent.com/Casterlabs/Platform-SDKs/main/Kick/Badges/Other/TrainwrecksTV.svg");
        }

        return badges;
    }

}
