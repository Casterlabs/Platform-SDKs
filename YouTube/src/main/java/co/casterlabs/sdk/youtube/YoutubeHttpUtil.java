package co.casterlabs.sdk.youtube;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.NonNull;

public class YoutubeHttpUtil {

    public static JsonObject list(@NonNull String address, @Nullable YoutubeAuth auth) throws IOException, ApiException, ApiAuthException {
        HttpResponse<JsonObject> response = WebRequest.sendHttpRequest(
            HttpRequest
                .newBuilder()
                .uri(URI.create(address))
                .header("X-Client-Type", "api"),
            RsonBodyHandler.of(JsonObject.class),
            auth
        );
        check(response);
        return response.body();
    }

    public static JsonObject delete(@NonNull String address, @Nullable YoutubeAuth auth) throws IOException, ApiException, ApiAuthException {
        HttpResponse<JsonObject> response = WebRequest.sendHttpRequest(
            HttpRequest
                .newBuilder()
                .uri(URI.create(address))
                .DELETE()
                .header("X-Client-Type", "api"),
            RsonBodyHandler.of(JsonObject.class),
            auth
        );
        check(response);
        return response.body();
    }

    public static JsonObject insert(@NonNull String body, @NonNull String contentType, @NonNull String address, @Nullable YoutubeAuth auth) throws IOException, ApiException, ApiAuthException {
        HttpResponse<JsonObject> response = WebRequest.sendHttpRequest(
            HttpRequest
                .newBuilder()
                .uri(URI.create(address))
                .POST(BodyPublishers.ofString(body))
                .header("Content-Type", contentType)
                .header("X-Client-Type", "api"),
            RsonBodyHandler.of(JsonObject.class),
            auth
        );
        check(response);
        return response.body();
    }

    private static void check(HttpResponse<JsonObject> response) throws ApiException, ApiAuthException {
        if (response.statusCode() >= 200 && response.statusCode() <= 299) {
            return; // We're successful!
        }

        if (response.statusCode() == 401) {
            throw new ApiAuthException(response.body().toString());
        } else {
            throw new ApiException(response.body().toString());
        }
    }

}
