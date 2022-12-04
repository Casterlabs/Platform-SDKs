package co.casterlabs.glimeshapijava.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class GlimeshSubscriber {
    public static final String GQL_DATA = "isActive,price,productName,streamer{" + GlimeshUser.GQL_DATA + "},user{" + GlimeshUser.GQL_DATA + "},id";
    private boolean isActive;
    private int price;
    private String productName;
    private GlimeshUser streamer;
    private GlimeshUser user;
    private String id;

}
