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
        public final Double value = null;
        public final String unit = null;
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class Dimensions {
        public final Double length = null;
        public final Double width = null;
        public final Double height = null;
        public final String unit = null;
    }

}
