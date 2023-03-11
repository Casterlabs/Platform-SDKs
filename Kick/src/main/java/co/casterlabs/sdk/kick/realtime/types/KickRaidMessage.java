package co.casterlabs.sdk.kick.realtime.types;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class KickRaidMessage {

    private String id;

//  @JsonField("optionalMessage") // mispelled.
    private @Nullable String optionalMessage;

//  @JsonField("numberOfViewers") // mispelled.
    private int numberOfViewers;

}
