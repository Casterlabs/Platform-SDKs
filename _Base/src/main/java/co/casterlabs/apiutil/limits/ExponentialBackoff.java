package co.casterlabs.apiutil.limits;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.ThreadLocalRandom;

import lombok.SneakyThrows;

public class ExponentialBackoff {
    // @formatter:off
    private static final long RESET_TIME = MINUTES.toMillis(1);
    private static final long MAX        = SECONDS.toMillis(8);
    private static final long INCREMENT  = /* millis */    500;
    private static final long JITTER     = /* millis */    150;
    // @formatter:on

    private long wait = 0;
    private long lastRequest = 0;

    public long getWaitTime() {
        long now = System.currentTimeMillis();

        if ((now - this.lastRequest) > RESET_TIME) {
            this.lastRequest = now;
            this.wait = 0;
            return ThreadLocalRandom.current().nextLong(JITTER, JITTER * 2); // We should "instantly" attempt.
        }

        this.lastRequest = now;

        if (this.wait < MAX) {
            this.wait += INCREMENT;
        }

        return this.wait + ThreadLocalRandom.current().nextLong(0, JITTER); // We should wait (+ jitter).
    }

    @SneakyThrows
    public void waitSleep() {
        Thread.sleep(this.getWaitTime());
    }

}
