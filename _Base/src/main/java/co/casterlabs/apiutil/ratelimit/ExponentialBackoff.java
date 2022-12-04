package co.casterlabs.apiutil.ratelimit;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ExponentialBackoff {
    // @formatter:off
    private static final long RESET_TIME = MINUTES.toMillis(1);
    private static final long MAX        = SECONDS.toMillis(5);
    private static final long INCREMENT  = /* millis */    500;
    private static final long INSTANT    = /* millis */    100;
    // @formatter:on

    private long wait = 0;
    private long lastRequest = 0;

    public long getWaitTime() {
        long now = System.currentTimeMillis();

        if ((now - this.lastRequest) > RESET_TIME) {
            this.lastRequest = now;
            this.wait = 0;
            return INSTANT; // We should "instantly" attempt a reconnect.
        }

        this.lastRequest = now;

        if (this.wait < MAX) {
            this.wait += INCREMENT;
        }

        return this.wait;
    }

}
