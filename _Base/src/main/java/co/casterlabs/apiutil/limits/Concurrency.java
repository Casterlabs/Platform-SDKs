package co.casterlabs.apiutil.limits;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Concurrency {
    private static final Map<String, Semaphore> semaphoreCache = new HashMap<>();
    private static int maxOutgoingPerDomain = -1;

    public static void setMaxOutgoingPerDomain(int value) {
        semaphoreCache.clear();
        maxOutgoingPerDomain = value;
    }

    public static <T> T execute(String domain, IOSupplier<T> supp) throws InterruptedException, IOException {
        Semaphore lock = null;
        if (maxOutgoingPerDomain > 0) {
            lock = semaphoreCache.get(domain);
            if (lock == null) {
                lock = new Semaphore(maxOutgoingPerDomain, true);
                semaphoreCache.put(domain, lock);
            }
        }

        if (lock != null) {
            lock.acquire();
        }

        try {
            return supp.get();
        } finally {
            if (lock != null) {
                lock.release();
            }
        }
    }

    public static interface IOSupplier<T> {

        T get() throws IOException, InterruptedException;

    }

}
