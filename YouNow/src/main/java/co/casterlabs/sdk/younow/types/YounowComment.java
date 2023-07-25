package co.casterlabs.sdk.younow.types;

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
public class YounowComment {
    private String comment;

    private int broadcasterId;

    private int broadcasterTierRank;

    private int userId;

    private String name;

    private String profileUrlString;

    private long timestamp;

    @JsonField("broadcasterMod")
    private boolean isModerator;

    private @Nullable SubscriptionData subscriptionData;

    public boolean isSubscriber() {
        return this.subscriptionData != null;
    }

    public boolean isBroadcaster() {
        return this.broadcasterId == this.userId;
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class SubscriptionData {
        private int badgesAssetRevision;
        private String badgeAssetSku;
    }

    public List<String> getBadges() {
        List<String> badges = new LinkedList<>();

        if (this.isBroadcaster()) {
            badges.add(String.format("https://ynassets.younow.com/tiersRank/badges/live/%d.svg?assetRevision=2", this.broadcasterTierRank));
        }

        if (this.isModerator()) {
            badges.add("https://cdn.younow.com/angularjsapp/src/assets/images/icons_v3/mymod-chat-badge.png");
        }

        if (this.isSubscriber()) {
            badges.add(String.format("https://ynassets.younow.com/subscriptionsTiers/usersAssets/live/%d/%s/web_%s.png?assetRevision=%d", this.broadcasterId, this.subscriptionData.badgeAssetSku, this.subscriptionData.badgeAssetSku, this.subscriptionData.badgesAssetRevision));
        }

        return badges;
    }

}
