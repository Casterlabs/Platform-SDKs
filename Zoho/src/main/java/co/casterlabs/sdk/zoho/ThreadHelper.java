package co.casterlabs.sdk.zoho;

public class ThreadHelper {
    private static int threadCount = 0;

    public static void executeAsync(String name, Runnable run) {
        Thread t = new Thread(run);

        t.setName(name + " - ZohoApi Async Thread #" + threadCount++);
        t.start();
    }

    public static Thread executeAsyncLater(String name, Runnable run, long millis) {
        Thread t = (new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(millis);
                    run.run();
                } catch (InterruptedException ignored) {} // We ignore this so we can cancel waits.
            }
        });

        t.setName(name + " - ZohoApi Waiting Thread #" + threadCount++);
        t.setDaemon(true);
        t.start();

        return t;
    }

}
