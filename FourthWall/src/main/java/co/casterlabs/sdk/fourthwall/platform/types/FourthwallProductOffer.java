package co.casterlabs.sdk.fourthwall.platform.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class FourthwallProductOffer {
    public final String id = null;
    public final String name = null;
    public final String slug = null;
    public final String description = null;
    public final FourthwallProductVariant variant = null;
    public final PrimaryImage primaryImage = null;

    @ToString
    @JsonClass(exposeAll = true)
    public static class PrimaryImage {
        public final String id = null;
        public final String url = null;
        public final Integer width = null;
        public final Integer height = null;
        public final String transformedUrl = null;

    }

}
