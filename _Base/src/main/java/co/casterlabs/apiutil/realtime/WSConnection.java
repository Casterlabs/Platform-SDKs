package co.casterlabs.apiutil.realtime;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import co.casterlabs.apiutil.ratelimit.ExponentialBackoff;
import lombok.NonNull;
import lombok.SneakyThrows;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;

public class WSConnection implements Closeable {
    private URI uri;
    private WSListener listener;

    private FastLogger logger;
    private Connection conn;

    private ExponentialBackoff backoff = new ExponentialBackoff();

    @SneakyThrows
    public WSConnection(@NonNull String uri, @NonNull WSListener listener) {
        this.uri = new URI(uri);
        this.listener = listener;
        this.logger = new FastLogger("WSConnection (" + this.uri + ")");
    }

    public void send(@NonNull Object payload) {
        if (this.conn == null) {
            throw new IllegalStateException("You must close the connection before calling connect().");
        }

        this.conn.send(payload.toString());
    }

    public synchronized void connect() throws InterruptedException {
        if (this.conn == null) {
            long backoffTime = this.backoff.getWaitTime();
            Thread.sleep(backoffTime);

            this.conn = new Connection();
            this.conn.connectBlocking();
        } else {
            throw new IllegalStateException("You must close the connection before calling connect().");
        }
    }

    public boolean isOpen() {
        return this.conn != null;
    }

    @Override
    public void close() {
        doCleanup();
    }

    protected void doCleanup() {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (Exception ignored) {}
        }

        this.conn = null;
    }

    private class Connection extends WebSocketClient {
        private static final String DEBUG_WS_CONNECTED = "Connected \u2713";
        private static final String DEBUG_WS_DISCONNECTED = "Disconnected \u00d7";
        private static final String DEBUG_WS_SEND = "\u2191 %s";
        private static final String DEBUG_WS_RECIEVE = "\u2193 %s";

        public Connection() {
            super(WSConnection.this.uri);
        }

        @Override
        public void send(String payload) {
            logger.trace(String.format(DEBUG_WS_SEND, payload));
            super.send(payload);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            logger.trace(DEBUG_WS_CONNECTED);
            listener.onOpen();
        }

        @Override
        public final void onMessage(String raw) {
            logger.trace(String.format(DEBUG_WS_RECIEVE, raw));

            try {
                listener.onMessage(raw);
            } catch (Throwable t) {
                logger.severe("An uncaught exception occurred whilst processing frame: %s\n%s", raw, t);
            }
        }

        private void onClose() {
            logger.trace(DEBUG_WS_DISCONNECTED);
            doCleanup();
            new Thread(() -> listener.onClose());
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            this.onClose();
        }

        @Override
        public void onError(Exception e) {
            if ((e instanceof IOException) && e.getMessage().equals("Socket closed")) {
                this.onClose();
            } else {
                logger.severe("An uncaught exception occurred:\n%s", e);
            }
        }

    }

}
