package co.casterlabs.sdk.tiktok.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class TiktokUserInfo {

    @Deprecated
    @JsonExclude
    @ToString.Exclude
    public final long requestMadeAt = System.currentTimeMillis();

    @JsonField("open_id")
    public final String openId = null;

    @JsonField("union_id")
    public final String unionId = null;

    @JsonField("avatar_url")
    public final String avatarUrl = null;

    @JsonField("bio_description")
    public final String bio = null;

    @JsonField("display_name")
    public final String displayName = null;

    @JsonField("follower_count")
    public final Integer followerCount = null;

    @JsonField("following_count")
    public final Integer followingCount = null;

    @JsonField("likes_count")
    public final Integer likesCount = null;

    @JsonField("is_verified")
    public final Boolean isVerified = null;

    @JsonField("profile_deep_link")
    public final String profileDeepLink = null;

}
