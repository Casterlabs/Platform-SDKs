package co.casterlabs.sdk.caffeine.requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.caffeine.CaffeineApi;
import co.casterlabs.sdk.caffeine.CaffeineEndpoints;
import co.casterlabs.sdk.caffeine.HttpUtil;
import co.casterlabs.sdk.caffeine.types.CaffeineGame;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import okhttp3.Response;

public class CaffeineGamesListRequest extends WebRequest<List<CaffeineGame>> {

    @Override
    protected List<CaffeineGame> execute() throws ApiException, IOException {
        try (Response response = HttpUtil.sendHttpGet(CaffeineEndpoints.GAMES_LIST, null)) {
            String body = response.body().string();

            response.close();

            JsonArray array = CaffeineApi.RSON.fromJson(body, JsonArray.class);
            List<CaffeineGame> list = new ArrayList<>();

            for (JsonElement element : array) {
                CaffeineGame game = CaffeineApi.RSON.fromJson(element, CaffeineGame.class);

                list.add(game);
            }

            return list;
        } catch (JsonParseException e) {
            throw new ApiException(e);
        }
    }

}
