package co.casterlabs.sdk.youtube.types;

import java.time.Instant;
import java.util.Map;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveBroadcastSnippet {
    public final String title = null;
    public final String channelId = null;
    public final String description = null;
    public final Instant publishedAt = null;
    public final Boolean isDefaultBroadcast = null;
    public final String liveChatId = null;

    public final Instant scheduledStartTime = null;
    public final Instant scheduledEndTime = null;

    public final Instant actualStartTime = null;
    public final Instant actualEndTime = null;

    public final Map<String, YoutubeThumbnail> thumbnails = null;

}
