package co.casterlabs.sdk.kick.types;

import java.util.List;

import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class KickUser {
    public static final TypeToken<KickUser[]> ARRAY_TYPE = TypeToken.of(KickUser[].class);
    public static final TypeToken<List<KickUser>> LIST_TYPE = new TypeToken<List<KickUser>>() {
    };

    public final String email = null;
    public final String name = null;

    @JsonField("user_id")
    public final Integer id = null;

    @JsonField("profile_picture")
    public final String profilePictureUrl = null;

}
