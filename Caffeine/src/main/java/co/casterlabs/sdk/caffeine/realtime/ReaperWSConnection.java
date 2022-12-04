package co.casterlabs.sdk.caffeine.realtime;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

import co.casterlabs.apiutil.realtime.WSConnection;
import co.casterlabs.apiutil.realtime.WSListener;
import lombok.NonNull;

public class ReaperWSConnection implements Closeable {
    private static final long CAFFEINE_KEEPALIVE = TimeUnit.SECONDS.toMillis(5); // We keep this low because Reaper is unstable.

    private WSConnection conn;
    private WSListener proxiedListener;

    private Thread kaThread;

    public ReaperWSConnection(@NonNull String uri, @NonNull WSListener listener) {
        this.conn = new RWSC(uri, new Listener());
        this.proxiedListener = listener;
    }

    public void connect() throws InterruptedException {
        this.conn.connect();
    }

    public void send(Object payload) {
        this.conn.send(payload);
    }

    public boolean isOpen() {
        return this.conn.isOpen();
    }

    @Override
    public void close() {
        this.conn.close();
    }

    private class RWSC extends WSConnection {

        public RWSC(@NonNull String uri, @NonNull WSListener listener) {
            super(uri, listener);
        }

        @Override
        protected void doCleanup() {
            if (kaThread != null) {
                kaThread.interrupt();
                kaThread = null;
            }
        }

    }

    private class Listener implements WSListener {
        private long lastThanks;

        @Override
        public void onOpen() {
            this.lastThanks = System.currentTimeMillis(); // Reset.

            kaThread = new Thread(() -> {
                while (conn.isOpen()) {
                    long delta = System.currentTimeMillis() - this.lastThanks;

                    if (delta > (CAFFEINE_KEEPALIVE * 2)) {
                        // We timed out :(
                        conn.close();
                        return;
                    }

                    try {
                        Thread.sleep(CAFFEINE_KEEPALIVE);
                        conn.send("\"HEALZ\"");
                    } catch (Exception ignored) {}
                }
            });
            kaThread.start();

            proxiedListener.onOpen();
        }

        @Override
        public void onMessage(String raw) {
            if (raw.equals("\"THANKS\"")) { // Keep alive message.
                this.lastThanks = System.currentTimeMillis();
                return;
            }

            proxiedListener.onMessage(raw);
        }

        @Override
        public void onClose() {
            proxiedListener.onClose();
        }

    }

}
