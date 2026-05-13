package co.casterlabs.sdk.fourthwall.platform.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class FourthwallTrackingParams {
    public final String fbc = null;
    public final String fbp = null;
    public final String utm_source = null;
    public final String utm_medium = null;
    public final String utm_campaign = null;
    public final String utm_content = null;
    public final String utm_term = null;

}
