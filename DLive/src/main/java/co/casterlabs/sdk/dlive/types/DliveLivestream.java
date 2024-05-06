package co.casterlabs.sdk.dlive.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class DliveLivestream {
    public static final String GQL_DATA = "thumbnailUrl,title,createdAt,category{" + DliveCategory.GQL_DATA + "},language{" + DliveLanguage.GQL_DATA + "}";

    private String thumbnailUrl;
    private String title;
    private JsonElement createdAt;
    private DliveCategory category;
    private DliveLanguage language;

    public Instant getCreatedAt() {
        long value;
        if (this.createdAt.isJsonString()) {
            value = Long.parseLong(this.createdAt.getAsString());
        } else {
            value = this.createdAt.getAsNumber().longValue();
        }
        return Instant.ofEpochMilli(value);
    }

}
