package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.sdk.kick.KickAuth;

@JsonClass(exposeAll = true)
class _KickApi {
    private final JsonElement data = null;
    private final String message = null;

    static void request(HttpRequest.Builder request, KickAuth auth) throws ApiException, ApiAuthException, IOException {
        request(request, auth, (TypeToken<?>) null);
    }

    static <T> T request(HttpRequest.Builder request, KickAuth auth, Class<T> clazz) throws ApiException, ApiAuthException, IOException {
        return request(request, auth, TypeToken.of(clazz));
    }

    static <T> T request(HttpRequest.Builder request, KickAuth auth, TypeToken<T> type) throws ApiException, ApiAuthException, IOException {
        HttpResponse<_KickApi> response = WebRequest.sendHttpRequest(
            request,
            RsonBodyHandler.of(_KickApi.class),
            auth
        );

        if (response.statusCode() == 204) {
            return null;
        }

        _KickApi body = response.body();

        if (response.statusCode() == 401) {
            throw new ApiAuthException(body.message);
        }

        if (body.data == null || body.data.isJsonNull()) {
            throw new ApiException(body.message);
        }

        if (type == null) {
            return null;
        }

        try {
            return Rson.DEFAULT.fromJson(body.data, type);
        } catch (JsonParseException e) {
            throw new ApiException(e);
        }
    }

}
