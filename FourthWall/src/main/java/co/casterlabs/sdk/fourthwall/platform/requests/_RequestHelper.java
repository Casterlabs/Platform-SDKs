package co.casterlabs.sdk.fourthwall.platform.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.fourthwall.FourthwallAuth;

class _RequestHelper {

    static void checkError(HttpResponse<?> response) throws ApiException {
        if (response.statusCode() < 200 || response.statusCode() > 299) {
            throw new ApiException(String.format("%d: %s", response.statusCode(), response.body()));
        }
    }

    static HttpResponse<JsonObject> GET(String url, @Nullable FourthwallAuth auth) throws ApiException, ApiAuthException, IOException {
        HttpResponse<JsonObject> response = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET(),
            RsonBodyHandler.of(JsonObject.class),
            auth
        );

        checkError(response);
        return response;
    }

    static void DELETE(String url, @Nullable FourthwallAuth auth) throws ApiException, ApiAuthException, IOException {
        HttpResponse<String> response = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE(),
            BodyHandlers.ofString(),
            auth
        );

        checkError(response);
    }

    static HttpResponse<JsonObject> POST(String url, JsonObject body, @Nullable FourthwallAuth auth) throws ApiException, ApiAuthException, IOException {
        HttpResponse<JsonObject> response = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json"),
            RsonBodyHandler.of(JsonObject.class),
            auth
        );

        checkError(response);
        return response;
    }

}
