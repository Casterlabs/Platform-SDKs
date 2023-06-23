package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.types.KickStreamInfoCategory;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Request;

@Accessors(chain = true)
public class KickSearchForCategoryRequest extends WebRequest<List<KickStreamInfoCategory>> {
    private static final String API_TOKEN = "nXIMW0iEN6sMujFYjFuhdrSwVow3pDQu";

    private @Setter String query;

    @Override
    protected List<KickStreamInfoCategory> execute() throws ApiException, ApiAuthException, IOException {
        assert this.query != null : "You must specify something to query for.";

        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url("https://search.kick.com/collections/subcategory/documents/search?collections=subcategory&preset=category_list&q=" + this.query)
                .header("X-Typesense-Api-Key", API_TOKEN),
            null
        );

        JsonObject json = Rson.DEFAULT.fromJson(response, JsonObject.class);
        List<KickStreamInfoCategory> result = new LinkedList<>();

        for (JsonElement hit : json.getArray("hits")) {
            result.add(
                Rson.DEFAULT.fromJson(
                    hit.getAsObject().get("document"),
                    KickStreamInfoCategory.class
                )
            );
        }

        return result;
    }

}
