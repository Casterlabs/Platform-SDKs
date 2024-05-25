package co.casterlabs.apiutil.limits;

import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import lombok.NonNull;

public class Ratelimiter {
    private static final Map<String, ExponentialBackoff> ratelimiterCache = new HashMap<>();

    public static void ratelimitExponential(@NonNull String domain) {
        ExponentialBackoff backoff = ratelimiterCache.get(domain);

        if (backoff == null) {
            backoff = new ExponentialBackoff();
            ratelimiterCache.put(domain, backoff);
        }

        backoff.waitSleep();
    }

    /**
     * This falls back to {@link #ratelimitExponential(String)}
     */
    public static void ratelimitHeaders(String domain, HttpResponse<?> response) throws InterruptedException {
        HttpHeaders headers = response.headers();

        if (headers.firstValue("Ratelimit-Reset").isPresent()) {
            long resetAfter = headers.firstValueAsLong("Ratelimit-Reset").getAsLong();
            Thread.sleep(parseRatelimitValue(resetAfter));
        } else if (headers.firstValue("X-Ratelimit-Reset").isPresent()) {
            long resetAfter = headers.firstValueAsLong("X-Ratelimit-Reset").getAsLong();
            Thread.sleep(parseRatelimitValue(resetAfter));
        } else {
            ratelimitExponential(domain);
        }
    }

    /**
     * Guesses the header reset format and returns the milliseconds to sleep.
     */
    private static long parseRatelimitValue(long resetAfter) {
        long now = System.currentTimeMillis();

        // We need to determine if the value is Epoch Seconds, Epoch Millis, or Seconds.
        if (resetAfter > now) {
            // It can't be Epoch Seconds. Unless it's decades in the future. We'll assume
            // it's milliseconds since that's improbable for a ratelimit.
            resetAfter = System.currentTimeMillis() - resetAfter;
        } else if (resetAfter > now / 1000) {
            // It can't be Seconds. Unless it's decades.... Yada yada.
            resetAfter = System.currentTimeMillis() - resetAfter * 1000 /*s->ms*/;
        } else {
            // Has to be seconds.
            resetAfter *= 1000 /*s->ms*/;
        }

        return resetAfter;
    }

}
