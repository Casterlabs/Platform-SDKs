package co.casterlabs.sdk.x.v2.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class Xv2FollowActivityStreamPayload {

    public final Source source = null;

    public final Target target = null;

    @ToString
    @JsonClass(exposeAll = true)
    public static class Source {
        public final Xv2ActivityUser data = null;
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class Target {
        public final Xv2ActivityUser data = null;
    }

}
