package co.casterlabs.trovoapi.requests.data;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@NonNull
@ToString
@JsonClass(exposeAll = true)
public class TrovoSelfInfo {
    @JsonField("userName")
    private String username;

    @JsonField("nickName")
    private String nickname;

    @JsonField("userId")
    private String userId;

    private @Nullable String email;

    @JsonField("channelId")
    private String channelId;

    @JsonField("profilePic")
    private String profilePictureLink;

    @JsonField("info")
    private String streamerInfo;

}
