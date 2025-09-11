package co.casterlabs.apiutil.realtime;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.limits.ExponentialBackoff;
import co.casterlabs.commons.async.AsyncTask;
import co.casterlabs.commons.websocket.WebSocketClient;
import co.casterlabs.commons.websocket.WebSocketListener;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Setter;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;

public abstract class WSConnection implements Closeable {
    public static final ThreadFactory THREAD_FACTORY = Executors.defaultThreadFactory();

    private final ExponentialBackoff backoff = new ExponentialBackoff();
    private final FastLogger logger = new FastLogger("WSConnection (" + this.uri + ")");

    private @Setter(AccessLevel.PROTECTED) URI uri;
    private @Setter(AccessLevel.PROTECTED) Map<String, String> additionalHeaders = Collections.emptyMap();

    private Connection conn;

    private Thread keepAliveThread = null;

    private volatile boolean closeIsLocal = false;

    public synchronized void connect() throws InterruptedException, IOException {
        if (this.conn != null) {
            throw new IllegalStateException("You must close the connection before calling connect().");
        }

        this.closeIsLocal = false;
        long backoffTime = this.backoff.getWaitTime();
        Thread.sleep(backoffTime);

        this.conn = new Connection();
        this.conn.ws.connect(60_000, 15_000);
    }

    public boolean isOpen() {
        return this.conn != null && this.conn.ws.isConnected();
    }

    @Override
    public void close() {
        this.closeIsLocal = true;
        if (this.isOpen()) {
            try {
                this.conn.ws.close();
            } catch (Throwable ignored) {}
        }
        this.doCleanup();
    }

    private void doCleanup() {
        this.conn = null;
        this.keepAliveThread = null;
    }

    protected synchronized void doKeepAlive(long interval, Runnable run) {
        if (!this.isOpen()) throw new IllegalStateException("You must connect() before calling doKeepAlive().");
        if (this.keepAliveThread != null) throw new IllegalStateException("You can only call doKeepAlive() once per connect().");

        this.keepAliveThread = new Thread(() -> {
            Thread curr = Thread.currentThread();
            while (this.keepAliveThread == curr) {
                try {
                    run.run();
                    Thread.sleep(interval);
                } catch (InterruptedException ignored) {}
            }
        });
        this.keepAliveThread.setName("WSConnection KeepAlive");
        this.keepAliveThread.setDaemon(true);
        this.keepAliveThread.start();
    }

    /* ------------ */
    /* Abstract API */
    /* ------------ */

    protected abstract void onOpen();

    protected void onMessage(String raw) {}

    protected void onMessage(byte[] raw) {}

    protected abstract void onClose(boolean remote);

    /* ------------ */
    /* Internals    */
    /* ------------ */

    protected void send(@NonNull String payload) throws IOException {
        if (this.conn == null) {
            throw new IllegalStateException("You must connect() before calling send().");
        }
        this.conn.send(payload);
    }

    protected void send(@NonNull byte[] payload) throws IOException {
        if (this.conn == null) {
            throw new IllegalStateException("You must connect() before calling send().");
        }
        this.conn.send(payload);
    }

    private class Connection implements WebSocketListener {
        private static final String DEBUG_WS_CONNECTED = "Connected \u2713";
        private static final String DEBUG_WS_DISCONNECTED = "Disconnected \u00d7";
        private static final String DEBUG_WS_SEND = "\u2191 %s";
        private static final String DEBUG_WS_RECIEVE = "\u2193 %s";

        private WebSocketClient ws;

        public Connection() {
            this.ws = new WebSocketClient(WSConnection.this.uri, WSConnection.this.additionalHeaders);
            this.ws.setListener(this);
            this.ws.setThreadFactory(THREAD_FACTORY);
        }

        public void send(String payload) throws IOException {
            WSConnection.this.logger.trace(String.format(DEBUG_WS_SEND, payload));
            this.ws.send(payload);
        }

        public void send(byte[] payload) throws IOException {
            WSConnection.this.logger.trace(String.format(DEBUG_WS_SEND, "<binary>"));
            this.ws.send(payload);
        }

        @Override
        public void onOpen(WebSocketClient client, Map<String, String> headers, @Nullable String acceptedProtocol) {
            WSConnection.this.logger.trace(DEBUG_WS_CONNECTED);
            WSConnection.this.onOpen();
        }

        @Override
        public void onText(WebSocketClient client, String string) {
            WSConnection.this.logger.trace(String.format(DEBUG_WS_RECIEVE, string));
            try {
                WSConnection.this.onMessage(string);
            } catch (Throwable t) {
                logger.severe("An uncaught exception occurred whilst processing frame: %s\n%s", string, t);
            }
        }

        @Override
        public void onBinary(WebSocketClient client, byte[] bytes) {
            WSConnection.this.logger.trace(String.format(DEBUG_WS_RECIEVE, "<binary>"));
            try {
                WSConnection.this.onMessage(bytes);
            } catch (Throwable t) {
                logger.severe("An uncaught exception occurred whilst processing frame: <binary>\n%s", t);
            }
        }

        @Override
        public void onClosed(WebSocketClient client) {
            WSConnection.this.logger.trace(DEBUG_WS_DISCONNECTED);
            WSConnection.this.doCleanup();
            AsyncTask.create(() -> WSConnection.this.onClose(!WSConnection.this.closeIsLocal));
        }

        @Override
        public void onException(Throwable t) {
            WSConnection.this.logger.severe("An uncaught exception occurred:\n%s", t);
        }

    }

}
