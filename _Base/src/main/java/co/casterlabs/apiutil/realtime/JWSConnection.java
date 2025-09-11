package co.casterlabs.apiutil.realtime;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import co.casterlabs.apiutil.limits.ExponentialBackoff;
import co.casterlabs.commons.async.AsyncTask;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Setter;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;

public abstract class JWSConnection implements Closeable {
    public static final ThreadFactory THREAD_FACTORY = Executors.defaultThreadFactory();

    private final ExponentialBackoff backoff = new ExponentialBackoff();
    private final FastLogger logger = new FastLogger("WSConnection (" + this.uri + ")");

    private @Setter(AccessLevel.PROTECTED) URI uri;
    private @Setter(AccessLevel.PROTECTED) Map<String, String> additionalHeaders = Collections.emptyMap();

    private Connection conn;

    private Thread keepAliveThread = null;

    public synchronized void connect() throws InterruptedException, IOException {
        if (this.conn != null) {
            throw new IllegalStateException("You must close the connection before calling connect().");
        }

        long backoffTime = this.backoff.getWaitTime();
        Thread.sleep(backoffTime);

        this.conn = new Connection();
        this.conn.connect();
    }

    public boolean isOpen() {
        return this.conn != null && this.conn.isOpen();
    }

    @Override
    public void close() {
        if (this.isOpen()) {
            try {
                this.conn.close();
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

    private class Connection extends WebSocketClient {
        private static final String DEBUG_WS_CONNECTED = "Connected \u2713";
        private static final String DEBUG_WS_DISCONNECTED = "Disconnected \u00d7";
        private static final String DEBUG_WS_SEND = "\u2191 %s";
        private static final String DEBUG_WS_RECIEVE = "\u2193 %s";

        public Connection() {
            super(JWSConnection.this.uri, JWSConnection.this.additionalHeaders);
        }

        @Override
        public void send(String payload) {
            JWSConnection.this.logger.trace(String.format(DEBUG_WS_SEND, payload));
            super.send(payload);
        }

        @Override
        public void send(byte[] payload) {
            JWSConnection.this.logger.trace(String.format(DEBUG_WS_SEND, "<binary>"));
            super.send(payload);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            JWSConnection.this.logger.trace(DEBUG_WS_CONNECTED);
            JWSConnection.this.onOpen();
        }

        @Override
        public void onMessage(String string) {
            JWSConnection.this.logger.trace(String.format(DEBUG_WS_RECIEVE, string));
            try {
                JWSConnection.this.onMessage(string);
            } catch (Throwable t) {
                logger.severe("An uncaught exception occurred whilst processing frame: %s\n%s", string, t);
            }
        }

        @Override
        public void onMessage(ByteBuffer bytes) {
            JWSConnection.this.logger.trace(String.format(DEBUG_WS_RECIEVE, "<binary>"));
            try {
                JWSConnection.this.onMessage(bytes.array());
            } catch (Throwable t) {
                logger.severe("An uncaught exception occurred whilst processing frame: <binary>\n%s", t);
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            JWSConnection.this.logger.trace(DEBUG_WS_DISCONNECTED);
            JWSConnection.this.doCleanup();
            AsyncTask.create(() -> JWSConnection.this.onClose(remote));
        }

        @Override
        public void onError(Exception ex) {
            JWSConnection.this.logger.severe("An uncaught exception occurred:\n%s", ex);
        }

    }

}
