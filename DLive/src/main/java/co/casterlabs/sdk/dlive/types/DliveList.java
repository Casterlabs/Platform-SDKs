package co.casterlabs.sdk.dlive.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class DliveList {
    public static final String GQL_DATA = "totalCount";

    public final Integer totalCount = null;

}
