package co.casterlabs.tiktokapijava.unsupported.chat.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class TiktokScrapeViewersEvent {
    private long count;

}
