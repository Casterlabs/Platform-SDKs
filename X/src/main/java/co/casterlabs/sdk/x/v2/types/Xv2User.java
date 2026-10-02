package co.casterlabs.sdk.x.v2.types;

import java.time.Instant;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class Xv2User {
    public final @Nullable String id = null;

    @JsonField("created_at")
    public final Instant createdAt = null;

    public final String name = null;

    public final String username = null;

    public final @Nullable String description = null;

    @JsonField("public_metrics")
    public final PublicMetrics publicMetrics = null;

    @JsonField("profile_image_url")
    public final @Nullable String profileImageUrl = null;

    public final Boolean verified = null;

    @JsonField("subscriber_count")
    public final Integer subscriberCount = null;

    @JsonField("subscription_type")
    public final SubscriptionType subscriptionType = null;

    public boolean canLiveStream() {
        return this.subscriptionType == SubscriptionType.Premium || this.subscriptionType == SubscriptionType.PremiumPlus;
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class PublicMetrics {
        @JsonField("followers_count")
        public final Integer followersCount = null;
        @JsonField("following_count")
        public final Integer followingCount = null;
        @JsonField("listed_count")
        public final Integer listedCount = null;
        @JsonField("post_count")
        public final Integer postCount = null;
        @JsonField("like_count")
        public final Integer likeCount = null;
        @JsonField("media_count")
        public final Integer mediaCount = null;
    }

    public static enum SubscriptionType {
        None,
        Basic,
        Premium,
        PremiumPlus,
    }

}
