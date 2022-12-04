package co.casterlabs.glimeshapijava.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class GlimeshChatMessage {
    public static final String GQL_DATA = "id,message,user{" + GlimeshUser.GQL_DATA + "},channel{" + GlimeshChannel.GQL_DATA + "}";

    private GlimeshChannel channel;
    private String id;
    private String message;
    private GlimeshUser user;

}
