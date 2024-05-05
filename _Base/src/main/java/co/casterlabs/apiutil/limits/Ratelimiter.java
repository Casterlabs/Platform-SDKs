package co.casterlabs.apiutil.limits;

import java.util.HashMap;
import java.util.Map;

import lombok.NonNull;

public class Ratelimiter {
    private static final Map<String, ExponentialBackoff> ratelimiterCache = new HashMap<>();

    public static void ratelimitTMR(@NonNull String domain) {
        ExponentialBackoff backoff = ratelimiterCache.get(domain);

        if (backoff == null) {
            backoff = new ExponentialBackoff();
            ratelimiterCache.put(domain, backoff);
        }

        backoff.waitSleep();
    }

}
