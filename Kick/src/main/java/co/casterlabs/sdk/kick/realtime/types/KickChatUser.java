package co.casterlabs.sdk.kick.realtime.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class KickChatUser {

    private long id;

    private String username;

    @JsonField("profile_thumb")
    private String profilePicture;

    @JsonField("verified")
    private boolean isVerified;

    @JsonField("is_subscribed")
    private boolean isSubscribed;

    @JsonField("is_founder")
    private boolean isFounder;

//    @JsonField("isSuperAdmin") // mispelled
    private boolean isSuperAdmin;

}
