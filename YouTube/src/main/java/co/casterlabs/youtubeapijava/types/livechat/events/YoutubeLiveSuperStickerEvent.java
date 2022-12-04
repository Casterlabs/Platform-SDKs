package co.casterlabs.youtubeapijava.types.livechat.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveSuperStickerEvent extends YoutubeLiveEvent {
    private long amountMicros;
    private String currency;
    private String amountDisplayString;
    private int tier;

    private SuperStickerMetadata superStickerMetadata;

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class SuperStickerMetadata {
        private String stickerId;
        private String altText;
        private String language;

    }

}
