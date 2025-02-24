package co.casterlabs.sdk.youtube.types;

import java.time.Instant;
import java.util.Map;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeChannelSnippet {
    public final String id = null;

    public final String title = null;

    public final String description = null;

    @JsonField("published_at")
    public final Instant publishedAt = null;

//    @JsonExclude
    public final Map<String, YoutubeThumbnail> thumbnails = null;

//    @JsonDeserializationMethod("thumbnails")
//    private void $deserialize_thumbnails(JsonElement e) throws JsonValidationException, JsonParseException {
//        Map<String, YoutubeThumbnail> thumbs = new HashMap<>();
//
//        for (Map.Entry<String, JsonElement> entry : e.getAsObject().entrySet()) {
//            thumbs.put(
//                entry.getKey(),
//                Rson.DEFAULT.fromJson(entry.getValue(), YoutubeThumbnail.class)
//            );
//        }
//
//        this.thumbnails = Collections.unmodifiableMap(thumbs);
//    }

    public String channelUrl() {
        return String.format("https://youtube.com/channel/%s", this.id);
    }

}
