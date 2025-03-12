package co.casterlabs.sdk.kick.unsupported.realtime.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class UnsupportedKickRaidEvent {
    @JsonField("number_viewers")
    private int numberOfViewers;

    @JsonField("host_username")
    private String hostUsername;

}
