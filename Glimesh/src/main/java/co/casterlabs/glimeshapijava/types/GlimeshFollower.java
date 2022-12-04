package co.casterlabs.glimeshapijava.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class GlimeshFollower {
    public static final String GQL_DATA = "hasLiveNotifications,streamer{" + GlimeshUser.GQL_DATA + "},user{" + GlimeshUser.GQL_DATA + "},id";
    private boolean hasLiveNotifications;
    private GlimeshUser streamer;
    private GlimeshUser user;
    private String id;

}
