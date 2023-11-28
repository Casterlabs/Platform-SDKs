package co.casterlabs.apiutil.ratelimit;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import co.casterlabs.commons.async.AsyncTask;
import lombok.SneakyThrows;

public class BucketRateLimiter {
    private int available = 0;
    private int waiting = 0;

    public BucketRateLimiter(int bucketSize, int refillRate, TimeUnit unit) {
        doRefill(new WeakReference<>(this), bucketSize, refillRate, unit);
    }

    @SneakyThrows
    public synchronized void block() {
        if (this.available == 0) {
            this.waiting++;
            this.wait();
            this.waiting--;
        }

        this.available--;

        if (this.available > 0) {
            this.notify();
        }
    }

    public synchronized void setAvailable(int value) {
        this.available = value;

        this.notify();
    }

    public synchronized void addAvailable(int value) {
        this.available += value;

        this.notify();
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
