package co.casterlabs.sdk.twitch.helix.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@JsonClass(exposeAll = true)
public class HelixChannelInformation {

    @JsonField("broadcaster_id")
    private @NonNull String broadcasterId;

    @JsonField("broadcaster_language")
    private @NonNull String language;

    @JsonField("game_id")
    private @NonNull String gameId;

    @JsonField("game_name")
    private @NonNull String gameName;

    private @NonNull String title;

    private int delay;

    private @NonNull String[] tags;

}
