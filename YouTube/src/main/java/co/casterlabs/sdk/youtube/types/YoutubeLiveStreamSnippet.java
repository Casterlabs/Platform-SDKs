package co.casterlabs.sdk.youtube.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveStreamSnippet {
    public final String title = null;
    public final String channelId = null;
    public final String description = null;
    public final Instant publishedAt = null;
    public final Boolean isDefaultStream = null;

}
