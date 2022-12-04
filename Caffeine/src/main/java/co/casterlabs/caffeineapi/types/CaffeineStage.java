package co.casterlabs.caffeineapi.types;

import java.util.List;

import co.casterlabs.caffeineapi.CaffeineApi;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class CaffeineStage {
    @JsonField("id")
    private String stageId;

    private String username;

    private String title;

    private String broadcastId;

    public String contentRating;

    private String badge;

    private String hlsUrl;

    private String trailerUrl;

    private List<String> viewingOptions;

    private String currentViewingOption;

    private boolean reactionsDisabled;

    // These two are set down below.
    private boolean live;
    private boolean replay;

    public static CaffeineStage fromJson(JsonObject stage) throws JsonParseException {
        CaffeineStage result = CaffeineApi.RSON.fromJson(stage, CaffeineStage.class);

        result.live = result.badge.equals("LIVE");
        result.replay = result.badge.equals("REPLAY");

        return result;
    }

}
