package co.casterlabs.apiutil.ratelimit;

import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public abstract class RateLimiter {
    private int available = 0;
    private int waiting = 0;

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

}
