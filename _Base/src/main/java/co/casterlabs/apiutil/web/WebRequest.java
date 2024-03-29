package co.casterlabs.apiutil.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.apiutil.ratelimit.ExponentialBackoff;
import co.casterlabs.commons.async.Promise;
import lombok.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class WebRequest<T> {
    public static final OkHttpClient client = new OkHttpClient.Builder()
        .addNetworkInterceptor(
            (chain) -> chain.proceed(
                chain // OkHttp bug.
                    .request()
                    .newBuilder()
                    .removeHeader("Accept-Encoding")
                    .build()
            )
        )
        .build();

    private static final Map<String, ExponentialBackoff> ratelimiterCache = new HashMap<>();

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

        while (true) {
            try (Response response = client.newCall(builder.build()).execute()) {
                if (response.code() == 429 || response.code() == 420) {
                    response.close(); // Do this before a long wait.
                    ratelimitDomain(builder.getUrl$okhttp().host());
                    continue; // Try again.
                }

                return response.body().string();
            }
        }
    }

    public static void ratelimitDomain(@NonNull String domain) {
        ExponentialBackoff backoff = ratelimiterCache.get(domain);

        if (backoff == null) {
            backoff = new ExponentialBackoff();
            ratelimiterCache.put(domain, backoff);
        }

        backoff.waitSleep();
    }

}
