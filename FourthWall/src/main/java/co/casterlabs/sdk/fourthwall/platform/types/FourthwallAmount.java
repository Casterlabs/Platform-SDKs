package co.casterlabs.sdk.fourthwall.platform.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class FourthwallAmount {
    public final Double value = null;
    public final String currency = null;

}
