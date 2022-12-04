package co.casterlabs.sdk.theta.realtime.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class ThetaChatUser {
    private String id;
    private String username;

    @JsonField("avatar_url")
    private String avatarUrl;

}
