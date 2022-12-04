package co.casterlabs.twitchapi.helix.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@JsonClass(exposeAll = true)
public class HelixStream {
    private @NonNull String id;

    private @NonNull String language;

    private @NonNull String title;

    private @NonNull String type;

    @JsonField("user_name")
    private @NonNull String userName;

    @JsonField("started_at")
    private @NonNull String startedAt;

    @JsonField("viewer_count")
    private int viewerCount;

    @JsonField("thumbnail_url")
    private @NonNull String thumbnailUrl;

}
