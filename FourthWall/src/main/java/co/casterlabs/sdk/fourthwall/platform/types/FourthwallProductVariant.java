package co.casterlabs.sdk.fourthwall.platform.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class FourthwallProductVariant {
    public final String id = null;
    public final String name = null;
    public final String sku = null;
    public final Integer quantity = null;

    public final FourthwallAmount unitPrice = null;
    public final FourthwallAmount price = null;
    public final Weight weight = null;
    public final Dimensions dimensions = null;

    public final JsonObject attributes = null;

    @ToString
    @JsonClass(exposeAll = true)
    public static class Weight {
        public final String value = null;
        public final String unit = null;
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class Dimensions {
        public final String length = null;
        public final String width = null;
        public final String height = null;
        public final String unit = null;
    }

}
