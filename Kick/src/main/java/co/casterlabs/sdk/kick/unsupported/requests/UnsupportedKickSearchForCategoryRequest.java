package co.casterlabs.sdk.kick.unsupported.requests;

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
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.unsupported.types.UnsupportedKickStreamInfoCategory;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class UnsupportedKickSearchForCategoryRequest extends WebRequest<List<UnsupportedKickStreamInfoCategory>> {
    private static final String API_TOKEN = "nXIMW0iEN6sMujFYjFuhdrSwVow3pDQu";

    private @Setter String query;

    @Override
    protected List<UnsupportedKickStreamInfoCategory> execute() throws ApiException, ApiAuthException, IOException {
        assert this.query != null : "You must specify something to query for.";

        JsonObject json = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create("https://search.kick.com/collections/subcategory/documents/search?collections=subcategory&preset=category_list&q=" + this.query))
                .header("X-Typesense-Api-Key", API_TOKEN),
            RsonBodyHandler.of(JsonObject.class),
            null
        ).body();

        List<UnsupportedKickStreamInfoCategory> result = new LinkedList<>();

        for (JsonElement hit : json.getArray("hits")) {
            result.add(
                Rson.DEFAULT.fromJson(
                    hit.getAsObject().get("document"),
                    UnsupportedKickStreamInfoCategory.class
                )
            );
        }

        return result;
    }

}
