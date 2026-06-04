package co.casterlabs.sdk.fourthwall.platform.requests;

import java.io.IOException;
import java.net.http.HttpResponse;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.PaginatedResponse.Page;
import co.casterlabs.apiutil.web.PaginatedResponse.PageFunction;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;

abstract class _PaginationHelper<R> implements PageFunction<R> {

    protected abstract HttpResponse<JsonObject> request(int cursor) throws ApiAuthException, ApiException, IOException;

    protected abstract Class<R[]> generic();

    @Override
    public Page<R> next(@Nullable Object cursor) throws ApiException, ApiAuthException, IOException {
        int currentCursor = cursor == null ? 0 : (int) cursor;
        JsonObject body = this.request(currentCursor).body();

        R[] list = Rson.DEFAULT.fromJson(body.get("results"), this.generic());

        Integer newCursor = null;
        if (currentCursor < body.getNumber("totalPages").intValue() - 1) {
            newCursor = currentCursor + 1;
        }

        return new Page<>(list, newCursor);
    }

}
