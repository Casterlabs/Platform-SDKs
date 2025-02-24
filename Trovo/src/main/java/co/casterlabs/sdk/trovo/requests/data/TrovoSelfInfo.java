package co.casterlabs.sdk.trovo.requests.data;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class TrovoSelfInfo {
    @JsonField("userName")
    public final String username = null;

    @JsonField("nickName")
    public final String nickname = null;

    @JsonField("userId")
    public final String userId = null;

    public final @Nullable String email = null;

    @JsonField("channelId")
    public final String channelId = null;

    @JsonField("profilePic")
    public final String profilePicUrl = null;

    @JsonField("info")
    public final String streamerInfo = null;

}
