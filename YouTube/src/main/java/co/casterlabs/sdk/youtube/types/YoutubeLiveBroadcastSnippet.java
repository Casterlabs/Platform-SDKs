package co.casterlabs.sdk.youtube.types;

import java.time.Instant;
import java.util.Map;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveBroadcastSnippet {
    public final String id = null;
    public final String title = null;
    public final String channelId = null;
    public final String description = null;
    public final Instant publishedAt = null;
    public final Instant scheduledStartTime = null;
    public final Boolean isDefaultBroadcast = null;
    public final String liveChatId = null;

    public final Map<String, YoutubeThumbnail> thumbnails = null;

    public String videoUrl() {
        return String.format("https://youtu.be/%s", this.id);
    }

}