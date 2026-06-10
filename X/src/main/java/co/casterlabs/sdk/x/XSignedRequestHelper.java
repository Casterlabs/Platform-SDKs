package co.casterlabs.sdk.x;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;

public class XSignedRequestHelper {

    public static void checkError(HttpResponse<?> response) throws ApiException {
        if (response.statusCode() < 200 || response.statusCode() > 299) {
            throw new ApiException(String.format("%d: %s %s", response.statusCode(), response.headers(), response.body()));
        }
    }

    public static Response GET(String url, Xv1Auth auth) throws IOException, ApiException, ApiAuthException {
        URI uri = URI.create(url);

        HttpRequest.Builder request = HttpRequest
            .newBuilder()
            .uri(uri)
            .GET()
            .header("Accept-Encoding", "identity");

        auth.authenticateRequest(request, "GET", uri, null);

        HttpResponse<String> response = WebRequest.sendHttpRequest(
            request,
            BodyHandlers.ofString(),
            null
        );
        checkError(response);
        return new Response(response);
    }

    public static Response PUT_JSON(String url, JsonObject json, Xv1Auth auth) throws IOException, ApiException, ApiAuthException {
        URI uri = URI.create(url);
        String body = json.toString();

        HttpRequest.Builder request = HttpRequest
            .newBuilder()
            .uri(uri)
            .PUT(HttpRequest.BodyPublishers.ofString(body))
            .header("Content-Type", "application/json")
            .header("Accept-Encoding", "identity");

        // Important: null params.
        // application/json bodies are not part of the OAuth v1 signature parameter
        // string.
        auth.authenticateRequest(request, "PUT", uri, null);

        HttpResponse<String> response = WebRequest.sendHttpRequest(
            request,
            BodyHandlers.ofString(),
            null
        );
        checkError(response);
        return new Response(response);
    }

    public static Response POST_JSON(String url, JsonObject json, Xv1Auth auth) throws IOException, ApiException, ApiAuthException {
        URI uri = URI.create(url);
        String body = json.toString();

        HttpRequest.Builder request = HttpRequest
            .newBuilder()
            .uri(uri)
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .header("Content-Type", "application/json")
            .header("Accept-Encoding", "identity");

        // Important: null params here.
        // The JSON body is NOT part of the OAuth v1 signature.
        auth.authenticateRequest(request, "POST", uri, null);

        HttpResponse<String> response = WebRequest.sendHttpRequest(
            request,
            BodyHandlers.ofString(),
            null
        );
        checkError(response);
        return new Response(response);
    }

    public static Response POST_FORM(String url, Map<String, String> params, Xv1Auth auth) throws IOException, ApiException, ApiAuthException {
        URI uri = URI.create(url);
        String body = form(params);

        HttpRequest.Builder request = HttpRequest
            .newBuilder()
            .uri(uri)
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("Accept-Encoding", "identity");

        auth.authenticateRequest(request, "POST", uri, params);

        HttpResponse<String> response = WebRequest.sendHttpRequest(
            request,
            BodyHandlers.ofString(),
            null
        );
        checkError(response);
        return new Response(response);
    }

    public static record Response(HttpResponse<String> http) {

        public <T> T as(Class<T> clazz) throws JsonValidationException, JsonParseException {
            return Rson.DEFAULT.fromJson(this.http.body(), clazz);
        }

    }

    private static String form(@Nullable Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> e : params.entrySet()) {
            if (result.length() != 0) {
                result.append('&');
            }

            result
                .append(OAuthForm.encode(e.getKey()))
                .append('=')
                .append(OAuthForm.encode(e.getValue()));
        }

        return result.toString();
    }

    private static final class OAuthForm {
        private static final char[] HEX = "0123456789ABCDEF".toCharArray();

        private static String encode(String s) {
            StringBuilder result = new StringBuilder();

            for (byte b : s.getBytes(java.nio.charset.StandardCharsets.UTF_8)) {
                int c = b & 0xFF;

                if ((c >= 'A' && c <= 'Z') ||
                    (c >= 'a' && c <= 'z') ||
                    (c >= '0' && c <= '9') ||
                    c == '-' ||
                    c == '_' ||
                    c == '.' ||
                    c == '~') {
                    result.append((char) c);
                    continue;
                }

                result.append('%');
                result.append(HEX[(c & 0xF0) >> 4]);
                result.append(HEX[c & 0x0F]);
            }

            return result.toString();
        }
    }

}
