package co.casterlabs.youtubeapijava;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import lombok.NonNull;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    private static OkHttpClient client = new OkHttpClient();

    public static Response sendHttpGet(@NonNull String address, @Nullable YoutubeAuth auth) throws IOException {
        return sendHttp(null, null, address, auth, null);
    }

    public static Response sendHttp(@NonNull String body, @NonNull String address, @Nullable YoutubeAuth auth, @Nullable String mime) throws IOException {
        return sendHttp(RequestBody.create(body.getBytes(StandardCharsets.UTF_8)), "POST", address, auth, mime);
    }

    public static Response sendHttp(@Nullable RequestBody body, @Nullable String type, @NonNull String address, @Nullable YoutubeAuth auth, @Nullable String mime) throws IOException {
        Request.Builder builder = new Request.Builder().url(address);

        if (type != null) {
            switch (type.toUpperCase()) {
                case "POST": {
                    builder.post(body);
                    break;
                }

                case "PUT": {
                    builder.put(body);
                    break;
                }

                case "PATCH": {
                    builder.patch(body);
                    break;
                }

                case "DELETE": {
                    builder.delete();
                    break;
                }

                case "GET": {
                    break;
                }
            }
        }

        if (auth != null) {
            try {
                auth.authenticateRequest(builder);
            } catch (ApiAuthException e) {
                throw new IOException(e);
            }
        }

        if (mime != null) {
            builder.addHeader("Content-Type", mime);
        }

        builder.addHeader("x-client-type", "api");

        Request request = builder.build();
        Response response = client.newCall(request).execute();

        return response;
    }

    @SneakyThrows
    public static String encodeURIComponent(@NonNull String s) {
        return URLEncoder.encode(s, "UTF-8")
            .replaceAll("\\+", "%20")
            .replaceAll("\\%21", "!")
            .replaceAll("\\%27", "'")
            .replaceAll("\\%28", "(")
            .replaceAll("\\%29", ")")
            .replaceAll("\\%7E", "~");
    }

}
