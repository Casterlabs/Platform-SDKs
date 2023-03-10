package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.KickApi;
import co.casterlabs.sdk.kick.types.KickEmote;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Request;

@Accessors(chain = true)
public class KickGetChannelEmotesRequest extends WebRequest<List<KickEmote>> {
    private @Setter String slug;

    @Override
    protected List<KickEmote> execute() throws ApiException, ApiAuthException, IOException {
        assert this.slug != null : "You must specify a slug to query for.";

        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url("https://kick.com/emotes/" + this.slug),
            null
        );
        JsonArray sets = KickApi.RSON.fromJson(response, JsonArray.class);
        List<KickEmote> emotes = new LinkedList<>();

        for (JsonElement e : sets) {
            JsonObject set = e.getAsObject();

            emotes.addAll(
                KickApi.RSON.fromJson(
                    set.get("emotes"),
                    new TypeToken<List<KickEmote>>() {
                    }
                )
            );
        }

        return emotes;
    }

}
