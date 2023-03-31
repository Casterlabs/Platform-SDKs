package co.casterlabs.apiutil.web;

import java.io.IOException;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.commons.async.Promise;
import lombok.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class WebRequest<T> {
    protected static final OkHttpClient client = new OkHttpClient();

    public Promise<T> sendAsync() {
        return new Promise<>(this::send);
    }

    public T send() throws ApiException, ApiAuthException {
        try {
            return this.execute();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    protected abstract T execute() throws ApiException, ApiAuthException, IOException;

    public static String sendHttpRequest(@NonNull Request.Builder builder, @Nullable AuthProvider auth) throws IOException {
        if (auth != null) {
            try {
                auth.authenticateRequest(builder);
            } catch (ApiAuthException e) {
                throw new IOException(e);
            }
        }

        try (Response response = client.newCall(builder.build()).execute()) {
            return response.body().string();
        }
    }

}
