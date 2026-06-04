package co.casterlabs.sdk.fourthwall.platform.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class FourthwallDonation {
    public final String id = null;
    public final String shopId = null;
    public final String status = null;
    public final String email = null;
    public final String username = null;
    public final String message = null;

    public final Amounts amounts = null;

    public final Instant createdAt = null;
    public final Instant updatedAt = null;

    @ToString
    @JsonClass(exposeAll = true)
    public static class Amounts {
        public final FourthwallAmount total = null;
    }

}
