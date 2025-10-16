package co.casterlabs.sdk.kick.unsupported.types;

import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class UnsupportedKickCategory {

    @JsonField("category_id")
    private long id;

    private String name;

    private String slug;

    private List<String> tags;

}
