package co.casterlabs.sdk.younow.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class YounowChannel {
    private String userId;

    private String username;

    private String description;

    private @JsonExclude int fans;

    @JsonDeserializationMethod("totalFans")
    private void $deserialize_fans(JsonElement e) {
        this.fans = Integer.valueOf(e.getAsString());
    }

    private @JsonExclude int subscribersCount;

    @JsonDeserializationMethod("totalSubscribers")
    private void $deserialize_subscribersCount(JsonElement e) {
        this.subscribersCount = Integer.valueOf(e.getAsString());
    }

}
