package co.casterlabs.caffeineapi.types;

import java.util.List;

import co.casterlabs.caffeineapi.CaffeineEndpoints;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class CaffeineGame {
    private long id;
    private String name;
    private String description;
    private String website;
    private boolean supported;

    @JsonField("icon_image_path")
    private String iconImagePath;

    @JsonField("banner_image_path")
    private String bannerImagePath;

    @JsonField("process_names")
    private List<String> processNames;

    @JsonField("executable_name")
    private String excecutableName;

    @JsonField("window_title")
    private String windowTitle;

    @JsonField("is_capture_software")
    private boolean isCaptureSoftware;

    public String getIconImagePath() {
        return CaffeineEndpoints.IMAGES + this.iconImagePath;
    }

    public String getBannerImagePath() {
        return CaffeineEndpoints.IMAGES + this.bannerImagePath;
    }

}
