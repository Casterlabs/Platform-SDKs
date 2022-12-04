package co.casterlabs.sdk.twitch;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import lombok.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    private static final OkHttpClient client = new OkHttpClient();

    public static Response sendHttpGet(@NonNull String address, @Nullable Map<String, String> headers, @Nullable TwitchHelixAuth auth) throws IOException {
        return sendHttp((RequestBody) null, null, address, headers, null, auth);
    }

    public static Response sendHttp(@NonNull String body, @NonNull String address, @Nullable Map<String, String> headers, @Nullable String mime, @Nullable TwitchHelixAuth auth) throws IOException {
        return sendHttp(body, "POST", address, headers, mime, auth);
    }

    public static Response sendHttp(@NonNull String body, @NonNull String type, @NonNull String address, @Nullable Map<String, String> headers, @Nullable String mime, @Nullable TwitchHelixAuth auth) throws IOException {
        return sendHttp(RequestBody.create(body.getBytes(StandardCharsets.UTF_8)), type, address, headers, mime, auth);
    }

    public static Response sendHttp(@Nullable RequestBody body, @Nullable String type, @NonNull String address, @Nullable Map<String, String> headers, @Nullable String mime, @Nullable TwitchHelixAuth auth) throws IOException {
        Request.Builder builder = new Request.Builder().url(address);

        if ((body != null) && (type != null)) {
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
            }
        }

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        if (mime != null) {
            builder.addHeader("Content-Type", mime);
        }

        if (auth != null) {
            try {
                auth.authenticateRequest(builder);
            } catch (ApiAuthException e) {
                throw new IOException(e);
            }
        }

        Request request = builder.build();
        Response response = client.newCall(request).execute();

        return response;
    }

}
