package co.casterlabs.sdk.kick.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class KickLivestream {

    public final String slug = null;

    public final String language = null;

    @JsonField("broadcaster_user_id")
    public final Integer userId = null;

    @JsonField("channel_id")
    public final Integer channelId = null;

    @JsonField("stream_title")
    public final String title = null;

    @JsonField("thumbnail")
    public final String thumbnailUrl = null;

    @JsonField("is_live")
    public final Boolean isLive = null;

    @JsonField("has_mature_content")
    public final Boolean isMature = null;

    @JsonField("started_at")
    public final Instant startTime = null;

    @JsonField("viewer_count")
    public final Integer viewerCount = null;

    public final KickCategory category = null;

}
