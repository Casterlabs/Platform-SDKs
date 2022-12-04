package co.casterlabs.sdk.tiktok.unsupported.chat.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class TiktokScrapeShareEvent {
    private TiktokScrapeUser user;

}
