package co.casterlabs.sdk.kick.unsupported.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class UnsupportedKickStreamInfoCategory {

    @JsonExclude
    private long id;

    @JsonField("is_live")
    private boolean isLive;

    private String name;

    private String parent;

    private String slug;

    @JsonField("src")
    private String image;

    @JsonDeserializationMethod("id")
    private void $deserialize_id(JsonElement e) {
        this.id = Long.parseLong(e.getAsString());
    }

}
