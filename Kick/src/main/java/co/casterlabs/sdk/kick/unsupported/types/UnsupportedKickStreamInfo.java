package co.casterlabs.sdk.kick.unsupported.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class UnsupportedKickStreamInfo {

    @JsonField("is_mature")
    private boolean isMature;

    private String language;

    @JsonField("subcategoryId")
    private long category;

    @JsonField("subcategoryName")
    private String categoryName;

    private String title;

}
