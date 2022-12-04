package co.casterlabs.sdk.caffeine.types;

import java.time.Instant;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.sdk.caffeine.CaffeineApi;
import co.casterlabs.sdk.caffeine.CaffeineEndpoints;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class CaffeineBroadcast {
    private String id;

    private String name;

    @JsonField("game_image_path")
    private String gameImage;

    @JsonField("webcam_image_path")
    private String webcamImage;

    @JsonField("preview_image_path")
    private String previewImage;

    private String state;

    private String platform;

    private BroadcastRating rating;

    @JsonField("online_at")
    private Instant onlineAt;

    private CaffeineUser user;

    private CaffeineGame game;

    @JsonDeserializationMethod("content_rating")
    private void $deserialize_rating(JsonElement e) {
        String value = e.getAsString().toString();

        for (BroadcastRating rating : BroadcastRating.values()) {
            if (rating.getApi().equals(value)) {
                this.rating = rating;
                return;
            }
        }

        this.rating = BroadcastRating.UNRATED; // Just in case.
    }

    public static CaffeineBroadcast fromJson(JsonElement element) throws ApiException {
        try {
            CaffeineBroadcast prop = CaffeineApi.RSON.fromJson(element, CaffeineBroadcast.class);

            prop.gameImage = CaffeineEndpoints.IMAGES + prop.gameImage;
            prop.webcamImage = CaffeineEndpoints.IMAGES + prop.webcamImage;
            prop.previewImage = CaffeineEndpoints.IMAGES + prop.previewImage;

            return prop;
        } catch (JsonParseException e) {
            throw new ApiException(e);
        }
    }

}
