package co.casterlabs.sdk.glimesh.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class GlimeshSocial {
    private String id;
    private String identifier;
    // private Instant insertedAt;
    private String platform;
    // private Instant updatedAt;
    private String username;

}
