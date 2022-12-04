package co.casterlabs.dliveapijava.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class DliveLanguage {
    public static final String GQL_DATA = "backendID,language,code";

    private int backendId;
    private String language;
    private String code;

}
