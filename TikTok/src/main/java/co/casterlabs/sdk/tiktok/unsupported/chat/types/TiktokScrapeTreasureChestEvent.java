package co.casterlabs.sdk.tiktok.unsupported.chat.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class TiktokScrapeTreasureChestEvent {
    private TiktokScrapeUser user;
    private TiktokScrapeTreasureChest chest;

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class TiktokScrapeTreasureChest {
        private double coinValue;
        private double diamondValue;
        private double usdValue;

    }

}
