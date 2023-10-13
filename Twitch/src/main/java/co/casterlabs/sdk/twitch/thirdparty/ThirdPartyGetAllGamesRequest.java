package co.casterlabs.sdk.twitch.thirdparty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.HttpUtil;
import co.casterlabs.sdk.twitch.thirdparty.ThirdPartyGetAllGamesRequest.TwitchGame;
import lombok.Getter;
import lombok.ToString;
import okhttp3.Response;

/**
 * Warning: This request takes multiple seconds to complete. You have been
 * warned.
 */
public class ThirdPartyGetAllGamesRequest extends WebRequest<List<TwitchGame>> {

    @Override
    public List<TwitchGame> execute() throws ApiException, ApiAuthException, IOException {
        try (Response response = HttpUtil.sendHttpGet("https://raw.githubusercontent.com/Nerothos/TwithGameList/master/game_info.json", null, null)) {
            if (response.code() == 200) {
                // There's so many items that we require custom logic to make it process in a
                // timely manner.
                JsonArray array = Rson.DEFAULT.fromJson(
                    response.body().string(),
                    JsonArray.class
                );

                List<TwitchGame> result = new ArrayList<>(array.size());

                for (JsonElement e : array) {
                    JsonObject game = (JsonObject) e;

                    String id = game.getString("id");
                    String name = game.getString("name");

                    result.add(new TwitchGame(id, name));
                }

                return result;
            } else {
                throw new ApiException("Unable to get all games: " + response.body().string());
            }
        }
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
