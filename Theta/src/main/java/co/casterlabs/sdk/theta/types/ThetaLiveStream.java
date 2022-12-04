package co.casterlabs.sdk.theta.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class ThetaLiveStream {
    private String title;
    private ThetaGame game;

    @JsonField("thumbnail_url")
    private String thumbnailUrl;

}
