package co.casterlabs.sdk.dlive.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class DliveList {
    public static final String GQL_DATA = "totalCount";

    private int totalCount;

}
