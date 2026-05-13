package co.casterlabs.sdk.fourthwall.platform.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class FourthwallGiftCardUse {
    public final String code = null;
    public final FourthwallAmount amountUsed = null;

}
