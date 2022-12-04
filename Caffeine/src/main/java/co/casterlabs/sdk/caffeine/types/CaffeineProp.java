package co.casterlabs.sdk.caffeine.types;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.sdk.caffeine.CaffeineApi;
import co.casterlabs.sdk.caffeine.CaffeineEndpoints;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class CaffeineProp {
    private String id;

    private String name;

    @JsonField("credits_per_item")
    private int credits = -1;

    @JsonField("gold_cost")
    private int goldCost = -1;

    @JsonField("plural_name")
    private String pluralName;

    @JsonField("universal_video_prop_path")
    private String universalVideoPropPath;

    @JsonField("preview_image_path")
    private String previewImagePath;

    @JsonField("static_image_path")
    private String staticImagePath;

    @JsonField("web_asset_path")
    private String webAssetPath;

    @JsonField("scene_kit_path")
    private String sceneKitPath;

    public static CaffeineProp fromJson(JsonElement element) throws ApiException {
        try {
            CaffeineProp prop = CaffeineApi.RSON.fromJson(element, CaffeineProp.class);

            prop.universalVideoPropPath = CaffeineEndpoints.ASSETS + prop.universalVideoPropPath;
            prop.previewImagePath = CaffeineEndpoints.ASSETS + prop.previewImagePath;
            prop.staticImagePath = CaffeineEndpoints.ASSETS + prop.staticImagePath;
            prop.webAssetPath = CaffeineEndpoints.ASSETS + prop.webAssetPath;
            prop.sceneKitPath = CaffeineEndpoints.ASSETS + prop.sceneKitPath;

            // The format from messages is different from the proplist api, so we detect
            // that and adjust to it.
            if (prop.goldCost == -1) {
                prop.goldCost = prop.credits / 3;
            } else {
                prop.credits = prop.goldCost * 3;
            }

            return prop;
        } catch (JsonParseException e) {
            throw new ApiException(e);
        }
    }

}
