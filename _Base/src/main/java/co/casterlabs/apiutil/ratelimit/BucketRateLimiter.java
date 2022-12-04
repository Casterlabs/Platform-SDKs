package co.casterlabs.apiutil.ratelimit;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import co.casterlabs.commons.async.AsyncTask;

public class BucketRateLimiter extends RateLimiter {

    public BucketRateLimiter(int bucketSize, int refillRate, TimeUnit unit) {
        doRefill(new WeakReference<>(this), bucketSize, refillRate, unit);
    }

    private static void doRefill(WeakReference<BucketRateLimiter> $ref, int bucketSize, int refillRate, TimeUnit unit) {
        AsyncTask.create(() -> {
            BucketRateLimiter instance;

            while ((instance = $ref.get()) != null) {
                instance.setAvailable(bucketSize);

                try {
                    Thread.sleep(unit.toMillis(refillRate));
                } catch (InterruptedException e) {}
            }
        });
    }

}
