package co.casterlabs.sdk.fourthwall.platform.requests;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.PaginatedResponse.Page;
import co.casterlabs.apiutil.web.PaginatedResponse.PageFunction;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.fourthwall.FourthwallAuth;

public abstract class _PaginationHelper<R> implements PageFunction<R> {

    protected abstract HttpRequest.Builder request(int cursor);

    protected abstract FourthwallAuth auth();

    protected abstract Class<R[]> generic();

    @Override
    public Page<R> next(@Nullable Object cursor) throws ApiException, ApiAuthException, IOException {
        int currentCursor = cursor == null ? 0 : (int) cursor;

        HttpResponse<JsonObject> response = WebRequest.sendHttpRequest(
            this.request(currentCursor),
            RsonBodyHandler.of(JsonObject.class),
            this.auth()
        );

        if (response.statusCode() < 200 || response.statusCode() > 299) {
            throw new ApiException(response.body().toString());
        }

        JsonObject body = response.body();

        R[] list = Rson.DEFAULT.fromJson(body.get("results"), this.generic());

        Integer newCursor = null;
        if (currentCursor < body.getNumber("totalPages").intValue() - 1) {
            newCursor = currentCursor + 1;
        }

        return new Page<>(list, newCursor);
    }

}
