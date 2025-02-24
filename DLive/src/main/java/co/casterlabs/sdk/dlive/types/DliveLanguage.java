package co.casterlabs.sdk.dlive.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class DliveLanguage {
    public static final String GQL_DATA = "backendID,language,code";

    public final Integer backendId = null;
    public final String language = null;
    public final String code = null;

}
