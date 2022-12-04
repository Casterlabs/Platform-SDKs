package co.casterlabs.twitchapi.helix.types;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@JsonClass(exposeAll = true)
public class HelixUser {
    private @NonNull String id;

    private @NonNull String login;

    private @NonNull String description;

    private @NonNull String type;

    private @Nullable String email;

    @JsonField("display_name")
    private @NonNull String displayName;

    @JsonField("broadcaster_type")
    private @NonNull String broadcasterType;

    @JsonField("profile_image_url")
    private @NonNull String profileImageUrl;

    @JsonField("offline_image_url")
    private @NonNull String offlineImageUrl;

    @JsonField("view_count")
    private long viewCount;

}
