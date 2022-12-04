package co.casterlabs.tiktokapijava.unsupported.chat.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class TiktokScrapeStickerEvent {
    private TiktokScrapeUser user;
    private TiktokScrapeSticker sticker;

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class TiktokScrapeSticker {
        private String id;
        private String imageUrl;

    }

}
