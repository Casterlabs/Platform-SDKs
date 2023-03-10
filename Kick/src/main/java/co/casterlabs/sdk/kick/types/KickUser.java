package co.casterlabs.sdk.kick.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class KickUser {

    private long id;

    private String username;

    private String bio;

    @JsonField("profile_pic")
    private String profilePicture;

}
