package co.casterlabs.sdk.kick.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;

@JsonClass(exposeAll = true)
public class KickStream {

    public final String key = null;

    public final String language = null;

    public final String url = null;

    @JsonField("is_live")
    public final Boolean isLive = null;

    @JsonField("is_mature")
    public final Boolean isMature = null;

    @JsonField("start_time")
    public final Instant startTime = null;

    @JsonField("viewer_count")
    public final Integer viewerCount = null;

}
