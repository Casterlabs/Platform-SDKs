package co.casterlabs.glimeshapijava.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class GlimeshCategory {
    private String name;
    private GlimeshCategory parent;
    private String slug;
    private String tagName;

}
