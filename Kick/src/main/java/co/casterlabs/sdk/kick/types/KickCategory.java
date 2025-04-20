package co.casterlabs.sdk.kick.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class KickCategory {
    public final Integer id = null;
    public final String name = null;

    @JsonField("thumbnail")
    public final String thumbnailUrl = null;

}
