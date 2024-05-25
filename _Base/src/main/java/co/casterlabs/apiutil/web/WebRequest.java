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

    public static <T> HttpResponse<T> sendHttpRequest(@NonNull HttpRequest.Builder builder, @NonNull BodyHandler<T> bodyHandler, @Nullable AuthProvider<?> auth) throws IOException {
        try {
            HttpRequest request = builder.build();

            final int MAX_RETRIES = 20;
            int retryCount = 0;
            while (true) {
                if (auth != null) {
                    try {
                        auth.authenticateRequest(builder);
                    } catch (ApiAuthException e) {
                        throw new IOException(e);
                    }
                }

                HttpResponse<T> response = Concurrency.execute(request.uri().getHost(), () -> client.send(request, bodyHandler));

                if (response.statusCode() == 429 || response.statusCode() == 420) {
                    if (request.uri().toString().contains("/helix/eventsub") && request.uri().toString().contains("twitch")) {
                        // Twitch's EventSub incorrectly returns 429 when you have too many
                        // subscriptions. So we'll just bail-out entirely.
                        return response;
                    }

                    if (retryCount == MAX_RETRIES) {
                        throw new IOException(
                            "Retried 20 times without success."
                                + "\nRequest uri: " + response.uri()
                                + "\nResponse headers: " + response.headers()
                                + "\nResponse status: " + response.statusCode()
                        );
                    }

                    Ratelimiter.ratelimitHeaders(request.uri().getHost(), response);
                    retryCount++;
                    continue; // Try again.
                }

                return response;
            }
        } catch (InterruptedException e) {
            Thread.interrupted(); // Clear.
            throw new IOException(e);
        }
    }

}
