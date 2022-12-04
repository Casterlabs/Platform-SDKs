package co.casterlabs.glimeshapijava.requests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.glimeshapijava.GlimeshApiJava;
import co.casterlabs.glimeshapijava.GlimeshAuth;
import lombok.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class GlimeshHttpUtil {
    private static OkHttpClient client = new OkHttpClient();

    public static Response sendHttp(@Nullable String body, @NonNull String url) throws IOException {
        Request.Builder builder = new Request.Builder().url(url);

        if (body != null) {
            builder.post(RequestBody.create(body.getBytes(StandardCharsets.UTF_8)));
        }

        Request request = builder.build();
        Response response = client.newCall(request).execute();

        return response;
    }

    public static Response sendHttp(@NonNull String query, @Nullable GlimeshAuth auth) throws IOException {
        Request.Builder builder = new Request.Builder().url(GlimeshApiJava.GLIMESH_API);

        builder.post(RequestBody.create(GlimeshApiJava.formatQuery(query).getBytes(StandardCharsets.UTF_8)));

        if (auth != null) {
            try {
                auth.authenticateRequest(builder);
            } catch (ApiAuthException e) {
                throw new IOException(e);
            }
        }

        builder.addHeader("Content-Type", "application/json");

        Request request = builder.build();
        Response response = client.newCall(request).execute();

        return response;
    }

}
