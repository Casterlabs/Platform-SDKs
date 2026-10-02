package co.casterlabs.sdk.x.v2.requests;

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
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.sdk.x.Xv2Auth;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class _ApiHelper {

    public static void request(HttpRequest.Builder request, Xv2Auth auth) throws ApiException, ApiAuthException, IOException {
        request(request, auth, (TypeToken<?>) null);
    }

    public static <T> T request(HttpRequest.Builder request, Xv2Auth auth, Class<T> clazz) throws ApiException, ApiAuthException, IOException {
        return request(request, auth, TypeToken.of(clazz));
    }

    public static <T> T request(HttpRequest.Builder request, Xv2Auth auth, TypeToken<T> type) throws ApiException, ApiAuthException, IOException {
        HttpResponse<JsonObject> response = WebRequest.sendHttpRequest(
            request,
            RsonBodyHandler.of(JsonObject.class),
            auth
        );

        if (response.statusCode() == 204) {
            return null;
        }

        JsonObject body = response.body();

//        System.out.println(response.statusCode());
//        System.out.println(body);
//        System.out.println(body.data);

        if (response.statusCode() == 401) {
            throw new ApiAuthException(body.toString());
        }

        if (response.statusCode() < 200 || response.statusCode() > 299) {
            throw new ApiException(body.toString());
        }

        if (type == null) {
            return null;
        }

        try {
            return Rson.DEFAULT.fromJson(body, type);
        } catch (JsonParseException e) {
            throw new ApiException(e);
        }
    }

}
