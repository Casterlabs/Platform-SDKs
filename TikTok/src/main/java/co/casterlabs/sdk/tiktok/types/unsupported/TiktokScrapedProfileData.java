package co.casterlabs.sdk.tiktok.types.unsupported;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class TiktokScrapedProfileData {

    @JsonField("uniqueId")
    public final String handle = null;

    public final String nickname = null;

    @JsonField("avatarLarger")
    public final String avatarLink = null;

    @JsonField("signature")
    public final String bio = null;

    public final Boolean verified = null;

    public final Boolean privateAccount = null;

    public final String region = null;

    public final Long followerCount = null;

    public final Long heartCount = null;

}
