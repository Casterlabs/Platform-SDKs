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
public class YounowBroadcast {
    private @JsonExclude int viewers;

    @JsonDeserializationMethod("viewers")
    private void $deserialize_viewers(JsonElement e) {
        this.viewers = Integer.valueOf(e.getAsString());
    }

}
