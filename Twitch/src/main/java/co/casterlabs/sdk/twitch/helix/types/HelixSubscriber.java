package co.casterlabs.sdk.twitch.helix.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class HelixSubscriber extends HelixSimpleUser {

    @JsonField("gifter_id")
    public final String gifterId = null;

    @JsonField("gifter_login")
    public final String gifterLogin = null;

    @JsonField("gifter_name")
    public final String gifterDisplayName = null;

    @JsonField("is_gift")
    public final Boolean isGift = null;

    @JsonField("plan_name")
    public String planName = null;

    @JsonField("tier")
    public String tier = null;

}
