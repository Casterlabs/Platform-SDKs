package co.casterlabs.sdk.x.v1.types;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class Xv1User {
    @JsonField("id_str")
    public final @Nullable String id = null;

    public final @Nullable String name = null;

    @JsonField("screen_name")
    public final @Nullable String screenName = null;

    public final @Nullable String description = null;

    @JsonField("profile_image_url_https")
    public final @Nullable String profileImageUrlHttps = null;

    public final @Nullable Boolean verified = null;

    @JsonField("protected")
    public final @Nullable Boolean isProtected = null;

    @JsonField("followers_count")
    public final @Nullable Integer followersCount = null;

}
