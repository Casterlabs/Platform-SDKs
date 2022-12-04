package co.casterlabs.sdk.theta.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class ThetaChannelDetails {

    @JsonField("live_stream")
    public ThetaLiveStream liveDetails;

    private boolean isLive;

    @JsonField("follows")
    private int followerCount;

    @JsonDeserializationMethod("status")
    private void $deserialize_subscriberCount(JsonElement e) {
        this.isLive = e.getAsString().equalsIgnoreCase("on");
    }

}
