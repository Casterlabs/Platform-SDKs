package co.casterlabs.sdk.fourthwall.platform.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class FourthwallShopContactInfo {
    public final String shopEmail = null;
    public final String contactEmail = null;
    public final String transactionalEmail = null;
    public final String customerSupportEmail = null;

    public final String phone = null;
    public final String shopName = null;
    public final String domain = null;

    public final Location location = null;

    @ToString
    @JsonClass(exposeAll = true)
    public static class Location {
        public final String name = null;
        public final String address1 = null;
        public final String address2 = null;
        public final String city = null;
        public final String state = null;
        public final String zip = null;
        public final String country = null;

    }

}
