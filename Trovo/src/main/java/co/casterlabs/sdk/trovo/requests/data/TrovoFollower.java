package co.casterlabs.sdk.trovo.requests.data;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@NonNull
@ToString
@JsonClass(exposeAll = true)
public class TrovoFollower {
    private String username;

    private String nickname;

    @JsonField("profile_pic")
    private String profilePic;

    @JsonField("followed_at")
    private String followedAt;

}
