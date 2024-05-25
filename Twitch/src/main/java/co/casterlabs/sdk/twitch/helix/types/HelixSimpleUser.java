package co.casterlabs.sdk.twitch.helix.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class HelixSimpleUser {
    @JsonField("user_id")
    public final String id = null;

    @JsonField("user_login")
    public final String login = null;

    @JsonField("user_name")
    public final String displayName = null;

}
