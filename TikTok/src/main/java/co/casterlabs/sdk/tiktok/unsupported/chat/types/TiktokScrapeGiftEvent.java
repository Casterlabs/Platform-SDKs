package co.casterlabs.sdk.tiktok.unsupported.chat.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class TiktokScrapeGiftEvent {
    private TiktokScrapeUser user;
    private TiktokScrapeGift gift;
    private boolean isStreak;

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class TiktokScrapeGift {
        private int id;
        private String name;
        private String imageUrl;
        private double coinValue;
        private double diamondValue;
        private double usdValue;

    }

}
