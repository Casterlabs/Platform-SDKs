package co.casterlabs.sdk.kick.types;

import java.util.List;

import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class KickCategory {
    public static final TypeToken<KickCategory[]> ARRAY_TYPE = TypeToken.of(KickCategory[].class);
    public static final TypeToken<List<KickCategory>> LIST_TYPE = new TypeToken<List<KickCategory>>() {
    };

    public final Integer id = null;
    public final String name = null;

    @JsonField("thumbnail")
    public final String thumbnailUrl = null;

}
