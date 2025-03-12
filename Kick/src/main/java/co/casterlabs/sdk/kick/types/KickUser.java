package co.casterlabs.sdk.kick.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;

@JsonClass(exposeAll = true)
public class KickUser {
    public final String email = null;
    public final String name = null;

    @JsonField("user_id")
    public final Integer id = null;

    @JsonField("profile_picture")
    public final String profilePictureUrl = null;

}
