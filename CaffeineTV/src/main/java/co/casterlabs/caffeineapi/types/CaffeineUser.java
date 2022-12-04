package co.casterlabs.caffeineapi.types;

import co.casterlabs.caffeineapi.CaffeineApi;
import co.casterlabs.caffeineapi.CaffeineEndpoints;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class CaffeineUser {
    private String bio;

    private String username;

    private UserBadge badge;

    @JsonField("name")
    private String displayname;

    @JsonField("stage_id")
    private String stageID;

    @JsonField("broadcast_id")
    private String broadcastID;

    @JsonField("badges_image_paths")
    private String[] statusBadges;

    @JsonField("caid")
    private String CAID;

    @JsonField("followers_count")
    private long followersCount;

    @JsonField("following_count")
    private long followingCount;

    @JsonField("avatar_image_path")
    private String imageLink;

    public static CaffeineUser fromJson(JsonObject user) throws JsonParseException {
        CaffeineUser result = CaffeineApi.RSON.fromJson(user, CaffeineUser.class);

        // Caffeine's "no badge" is always null
        if (result.badge == null) {
            result.badge = UserBadge.NONE;
        }

        result.imageLink = CaffeineEndpoints.IMAGES + result.imageLink;  // Prepend the images endpoint to the link.

        if (result.statusBadges == null) {
            result.statusBadges = new String[0];
        } else {
            for (int i = 0; i < result.statusBadges.length; i++) {
                result.statusBadges[i] = CaffeineEndpoints.ASSETS + result.statusBadges[i];
            }
        }

        return result;
    }

}
