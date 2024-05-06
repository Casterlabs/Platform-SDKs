package co.casterlabs.apiutil.web;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.apiutil.limits.Concurrency;
import co.casterlabs.apiutil.limits.Ratelimiter;
import co.casterlabs.commons.async.promise.Promise;
import lombok.NonNull;

public abstract class WebRequest<T> {
    public static final HttpClient client = HttpClient.newHttpClient();

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

    public static <T> HttpResponse<T> sendHttpRequest(@NonNull HttpRequest.Builder builder, @NonNull BodyHandler<T> bodyHandler, @Nullable AuthProvider auth) throws IOException {
        if (auth != null) {
            try {
                auth.authenticateRequest(builder);
            } catch (ApiAuthException e) {
                throw new IOException(e);
            }
        }

        HttpRequest request = builder.build();

        while (true) {
            try {
                HttpResponse<T> response = Concurrency.execute(request.uri().getHost(), () -> client.send(request, bodyHandler));

                if (response.statusCode() == 429 || response.statusCode() == 420) {
                    Ratelimiter.ratelimitTMR(request.uri().getHost());
                    continue; // Try again.
                }

                return response;
            } catch (InterruptedException e) {
                Thread.interrupted();
                throw new IOException(e);
            }
        }
    }

}
