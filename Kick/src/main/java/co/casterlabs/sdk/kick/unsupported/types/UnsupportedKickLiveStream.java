package co.casterlabs.sdk.kick.unsupported.types;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickApi;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class UnsupportedKickLiveStream {

    @JsonField("session_title")
    private String title;

    private String language;

    @JsonExclude
    private @Nullable String thumbnail;

    @JsonDeserializationMethod("thumbnail")
    private void $deserialize_thumbnail(JsonElement e) {
        this.thumbnail = UnsupportedKickApi.parseResponsiveImage(e);
    }

    @JsonField("is_mature")
    private boolean isMature;

    @JsonField("viewer_count")
    private int viewersCount;

    private List<UnsupportedKickCategory> categories;

}
