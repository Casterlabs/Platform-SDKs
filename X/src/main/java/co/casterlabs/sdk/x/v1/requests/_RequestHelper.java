package co.casterlabs.sdk.x.v1.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.x.Xv1Auth;

class _RequestHelper {

    static HttpResponse<JsonObject> GET(String url, Xv1Auth auth) throws IOException, ApiException, ApiAuthException {
        URI uri = URI.create(url);

        HttpRequest.Builder request = HttpRequest
            .newBuilder()
            .uri(uri)
            .GET()
            .header("Accept-Encoding", "identity");

        auth.authenticateRequest(request, "GET", uri, null);

        return WebRequest.sendHttpRequest(
            request,
            RsonBodyHandler.of(JsonObject.class),
            null
        );
    }

    static HttpResponse<JsonObject> POST_FORM(String url, Map<String, String> params, Xv1Auth auth) throws IOException, ApiException, ApiAuthException {
        URI uri = URI.create(url);
        String body = form(params);

        HttpRequest.Builder request = HttpRequest
            .newBuilder()
            .uri(uri)
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("Accept-Encoding", "identity");

        auth.authenticateRequest(request, "POST", uri, params);

        return WebRequest.sendHttpRequest(
            request,
            RsonBodyHandler.of(JsonObject.class),
            null
        );
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
