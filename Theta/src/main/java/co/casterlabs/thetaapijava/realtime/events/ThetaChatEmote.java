package co.casterlabs.thetaapijava.realtime.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class ThetaChatEmote {
    private String code;

    @JsonField("image_url")
    private String imageUrl;

    @JsonField("large_image_url")
    private String largeImageUrl;

}
