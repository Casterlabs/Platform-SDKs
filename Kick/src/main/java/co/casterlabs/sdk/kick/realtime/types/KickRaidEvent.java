package co.casterlabs.sdk.kick.realtime.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class KickRaidEvent {
    @JsonField("number_viewers")
    private int numberOfViewers;

    @JsonField("host_username")
    private String hostUsername;

}
