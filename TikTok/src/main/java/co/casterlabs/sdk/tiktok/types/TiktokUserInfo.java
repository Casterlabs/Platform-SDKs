package co.casterlabs.sdk.tiktok.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class TiktokUserInfo {

    @Deprecated
    @JsonExclude
    @ToString.Exclude
    private long requestMadeAt = System.currentTimeMillis();

    @JsonField("open_id")
    private String openId;

    @JsonField("union_id")
    private String unionId;

    @JsonField("avatar_url")
    private String avatarUrl;

    @JsonField("bio_description")
    private String bio;

    @JsonField("display_name")
    private String displayName;

    @JsonField("follower_count")
    private int follower_count;

    @JsonField("following_count")
    private int followingCount;

    @JsonField("likes_count")
    private int likesCount;

    @JsonField("is_verified")
    private boolean isVerified;

    @JsonField("profile_deep_link")
    private String profileDeepLink;

}
