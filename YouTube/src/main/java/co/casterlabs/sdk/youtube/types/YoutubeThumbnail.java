package co.casterlabs.sdk.youtube.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;

@Getter
@JsonClass(exposeAll = true)
public class YoutubeThumbnail {
    private String url;
    private int width;
    private int height;

    @Override
    public String toString() {
        return this.url;
    }

}
