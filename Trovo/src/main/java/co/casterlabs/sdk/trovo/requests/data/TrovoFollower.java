package co.casterlabs.sdk.trovo.requests.data;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class TrovoFollower {
    public final String username = null;

    public final String nickname = null;

    @JsonField("profile_pic")
    public final String profilePicUrl = null;

    @JsonField("followed_at")
    public final String followedAt = null;

}
