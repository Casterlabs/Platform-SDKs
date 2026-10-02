package co.casterlabs.sdk.x.v2.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class Xv2ActivityUser {

    public final String id = null;

    public final String username = null;

    public final String name = null;

    @JsonField("profile_image_url")
    public final String profileImageUrl = null;

    @JsonField("verified")
    public boolean isVerified = false;

    public final Affiliation affiliation = null;

    @ToString
    @JsonClass(exposeAll = true)
    public static class Affiliation {

        public final String url = null;

        @JsonField("badge_url")
        public final String badgeUrl = null;

        public final String description = null;

    }

}
