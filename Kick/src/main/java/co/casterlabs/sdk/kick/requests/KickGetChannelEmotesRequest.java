package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.LinkedList;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.KickApi;
import co.casterlabs.sdk.kick.types.KickEmote;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class KickGetChannelEmotesRequest extends WebRequest<List<KickEmote>> {
    private @Setter String slug;

    @Override
    protected List<KickEmote> execute() throws ApiException, ApiAuthException, IOException {
        assert this.slug != null : "You must specify a slug to query for.";

        JsonArray sets = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(KickApi.API_BASE_URL + "/emotes/" + this.slug)),
            RsonBodyHandler.of(JsonArray.class),
            null
        ).body();

        List<KickEmote> emotes = new LinkedList<>();
        for (JsonElement e : sets) {
            JsonObject set = e.getAsObject();

            emotes.addAll(
                Rson.DEFAULT.fromJson(
                    set.get("emotes"),
                    new TypeToken<List<KickEmote>>() {
                    }
                )
            );
        }
        return emotes;
    }

}
