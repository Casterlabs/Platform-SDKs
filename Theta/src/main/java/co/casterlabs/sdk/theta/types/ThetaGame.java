package co.casterlabs.sdk.theta.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class ThetaGame {
    private String id;
    private String name;
    private String slug;

    @JsonField("thumbnail_url")
    private String thumbnailUrl;

    @JsonField("banner_url")
    private String bannerUrl;

    @JsonField("category_url")
    private String categoryUrl;

    @JsonField("logo_url")
    private String logoUrl;

}
