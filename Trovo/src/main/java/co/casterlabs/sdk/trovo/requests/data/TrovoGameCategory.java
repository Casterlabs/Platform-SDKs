package co.casterlabs.sdk.trovo.requests.data;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class TrovoGameCategory {
    public final String id = null;

    public final String name = null;

    @JsonField("short_name")
    public final String shortName = null;

    @JsonField("icon_url")
    public final String iconUrl = null;

    @JsonField("desc")
    public final String description = null;

}
