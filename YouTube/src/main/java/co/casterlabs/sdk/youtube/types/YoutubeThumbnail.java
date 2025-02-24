package co.casterlabs.sdk.youtube.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;

@JsonClass(exposeAll = true)
public class YoutubeThumbnail {
    public final String url = null;
    public final Integer width = null;
    public final Integer height = null;

    @Override
    public String toString() {
        return this.url;
    }

}
