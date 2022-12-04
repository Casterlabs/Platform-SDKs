package co.casterlabs.trovoapi.requests.data;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@NonNull
@ToString
@JsonClass(exposeAll = true)
public class TrovoGameCategory {
    private String id;

    private String name;

    @JsonField("short_name")
    private String shortName;

    @JsonField("icon_url")
    private String iconUrl;

    @JsonField("desc")
    private String description;

}
