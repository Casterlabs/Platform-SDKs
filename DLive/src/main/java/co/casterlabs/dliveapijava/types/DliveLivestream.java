package co.casterlabs.dliveapijava.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class DliveLivestream {
    public static final String GQL_DATA = "thumbnailUrl,title,createdAt,category{" + DliveCategory.GQL_DATA + "},language{" + DliveLanguage.GQL_DATA + "}";

    private String thumbnailUrl;
    private String title;
    private Instant createdAt;
    private DliveCategory category;
    private DliveLanguage language;

}
