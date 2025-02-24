package co.casterlabs.sdk.youtube.types.livechat.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveSuperStickerEvent extends YoutubeLiveEvent {
    public final Long amountMicros = null;
    public final String currency = null;
    public final String amountDisplayString = null;
    public final Integer tier = null;
    public final SuperStickerMetadata superStickerMetadata = null;

    @ToString
    @JsonClass(exposeAll = true)
    public static class SuperStickerMetadata {
        public final String stickerId = null;
        public final String altText = null;
        public final String language = null;

    }

}
