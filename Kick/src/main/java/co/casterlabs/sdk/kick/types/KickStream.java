package co.casterlabs.sdk.kick.types;

import java.time.Instant;
import java.util.List;

import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class KickStream {
    public static final TypeToken<KickStream[]> ARRAY_TYPE = TypeToken.of(KickStream[].class);
    public static final TypeToken<List<KickStream>> LIST_TYPE = new TypeToken<List<KickStream>>() {
    };

    public final String key = null;

    public final String language = null;

    public final String url = null;

    @JsonField("thumbnail")
    public final String thumbnailUrl = null;

    @JsonField("is_live")
    public final Boolean isLive = null;

    @JsonField("is_mature")
    public final Boolean isMature = null;

    @JsonField("start_time")
    public final Instant startTime = null;

    @JsonField("viewer_count")
    public final Integer viewerCount = null;

}
