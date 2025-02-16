package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.net.http.HttpRequest;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.PaginatedResponse.Page;
import co.casterlabs.apiutil.web.PaginatedResponse.PageFunction;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;

public abstract class _HelixPaginationHelper<R> implements PageFunction<R> {

    protected abstract HttpRequest.Builder request(@Nullable String cursor);

    protected abstract TwitchHelixAuth auth();

    protected abstract Class<R[]> generic();

    @Override
    public Page<R> next(@Nullable Object cursor) throws ApiException, ApiAuthException, IOException {
        JsonObject response = WebRequest.sendHttpRequest(
            this.request((String) cursor),
            RsonBodyHandler.of(JsonObject.class),
            this.auth()
        ).body();

        if (response != null && !response.containsKey("data")) {
            throw new ApiException(response.toString());
        }

        R[] list = Rson.DEFAULT.fromJson(response.get("data"), this.generic());

        String newCursor = null;
        if (response.containsKey("pagination") && response.getObject("pagination").containsKey("cursor")) {
            newCursor = response.getObject("pagination").getString("cursor");
        }

        return new Page<>(list, newCursor);
    }

}
