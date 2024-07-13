package co.casterlabs.sdk.twitch.helix.types;

import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class HelixStream {
    @JsonField("id")
    public final String streamId = null;

    @JsonField("user_id")
    public final String userId = null;

    @JsonField("user_login")
    public final String userLogin = null;

    @JsonField("user_name")
    public final String userDisplayName = null;

    @JsonField("game_id")
    public final String gameId = null;

    @JsonField("game_name")
    public final String gameName = null;

    public final String title = null;

    public final List<String> tags = null;

    @JsonField("thumbnail_url")
    public final String thumbnailUrl = null;

    public String formatThumbnailUrl(int width, int height) {
        return this.thumbnailUrl
            .replace("{width}", String.valueOf(width))
            .replace("{height}", String.valueOf(height));
    }

}
