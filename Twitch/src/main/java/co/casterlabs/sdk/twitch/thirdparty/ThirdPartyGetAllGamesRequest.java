package co.casterlabs.sdk.twitch.thirdparty;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.thirdparty.ThirdPartyGetAllGamesRequest.TwitchGame;
import lombok.Getter;
import lombok.ToString;

/**
 * Warning: This request takes multiple seconds to complete. You have been
 * warned.
 */
public class ThirdPartyGetAllGamesRequest extends WebRequest<List<TwitchGame>> {

    @Override
    public List<TwitchGame> execute() throws ApiException, ApiAuthException, IOException {
        JsonArray array = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder(URI.create("https://raw.githubusercontent.com/Nerothos/TwithGameList/master/game_info.json")),
            RsonBodyHandler.of(JsonArray.class),
            null
        ).body();

        List<TwitchGame> result = new ArrayList<>(array.size());

        for (JsonElement e : array) {
            JsonObject game = (JsonObject) e;

            String id = game.getString("id");
            String name = game.getString("name");

            result.add(new TwitchGame(id, name));
        }

        return result;
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class TwitchGame {
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

}
