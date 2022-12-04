package co.casterlabs.dliveapijava.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class DliveCategory {
    public static final String GQL_DATA = "backendID,title,imgUrl,coverImgUrl";

    private int backendID;
    private String title;
    private String imgUrl;
    private JsonElement coverImgUrl;

}
