package co.casterlabs.sdk.tiktok.types.unsupported;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class TiktokScrapedProfileData {

    @JsonField("uniqueId")
    private String handle;

    private String nickname;

    @JsonField("avatarLarger")
    private String avatarLink;

    @JsonField("signature")
    private String bio;

    private boolean verified;

    private boolean privateAccount;

    private String region;

    private long followerCount;

    private long heartCount;

}
