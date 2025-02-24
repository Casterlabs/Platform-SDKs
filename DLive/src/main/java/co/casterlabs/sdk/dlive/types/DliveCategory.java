package co.casterlabs.sdk.dlive.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class DliveCategory {
    public static final String GQL_DATA = "backendID,title,imgUrl,coverImgUrl";

    public final Integer backendID = null;
    public final String title = null;
    public final String imgUrl = null;
    public final JsonElement coverImgUrl = null;

}
