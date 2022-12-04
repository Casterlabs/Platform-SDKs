package co.casterlabs.twitchapi.thirdparty.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class TwitchGame {
    private String id;
    private String name;
    private String boxArtUrl;

    public TwitchGame(String id, String name) {
        this.id = id;
        this.name = name;

        // We gotta make the box art url ourselves.
        final int width = 288;
        final int height = 384;

        this.boxArtUrl = "https://static-cdn.jtvnw.net/ttv-boxart/" + this.id + "_IGDB-" + width + "x" + height + ".jpg";
    }

}
