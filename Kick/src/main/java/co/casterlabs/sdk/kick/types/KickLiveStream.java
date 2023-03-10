package co.casterlabs.sdk.kick.types;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.sdk.kick.KickApi;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class KickLiveStream {

    @JsonField("session_title")
    private String title;

    private String language;

    @JsonExclude
    private @Nullable String thumbnail;

    @JsonDeserializationMethod("thumbnail")
    private void $deserialize_thumbnail(JsonElement e) {
        this.thumbnail = KickApi.parseResponsiveImage(e);
    }

    @JsonField("is_mature")
    private boolean isMature;

    @JsonField("viewer_count")
    private int viewersCount;

    // TODO figure out how their category system works.
    // TODO figure out how their tags system works.

}
