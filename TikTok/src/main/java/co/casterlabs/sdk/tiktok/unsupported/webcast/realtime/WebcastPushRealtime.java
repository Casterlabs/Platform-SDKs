package co.casterlabs.sdk.tiktok.unsupported.webcast.realtime;

import java.io.Closeable;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import co.casterlabs.sdk.tiktok.unsupported.webcast.WebcastCookies;
import co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastPushFrame;
import co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastResponse;
import lombok.SneakyThrows;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class WebcastPushRealtime implements Closeable {
//    private @Setter @Nullable ChatListener listener;

    private Connection conn;

    public void connect(WebcastResponse wr, WebcastCookies cookies) {
        if (this.conn == null) {
            this.conn = new Connection(wr, cookies);
            this.conn.connect();
        } else {
            throw new IllegalStateException("You must close the connection before reconnecting.");
        }
    }

    public void connectBlocking(WebcastResponse wr, WebcastCookies cookies) throws InterruptedException {
        if (this.conn == null) {
            this.conn = new Connection(wr, cookies);
            this.conn.connectBlocking();
        } else {
            throw new IllegalStateException("You must close the connection before reconnecting.");
        }
    }

    public boolean isOpen() {
        return this.conn != null;
    }

    @Override
    public void close() {
        doCleanup();
    }

    private void doCleanup() {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (Exception ignored) {}
        }

        this.conn = null;
    }

    private class Connection extends WebSocketClient {
        private WebcastResponse wr;

        public Connection(WebcastResponse wr, WebcastCookies cookies) {
            super(URI.create(wr.getPushServer()), new Draft_6455(), Map.of("Cookie", cookies.getCookie()));
            this.wr = wr;
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            FastLogger.logStatic(LogLevel.TRACE, "Open!");

            Thread t = new Thread(() -> {
                while (this.isOpen()) {
                    try {
//                        this.sendMessage("PING", null);
                        Thread.sleep(this.wr.getFetchInterval());
                    } catch (Exception ignored) {}
                }
            });

            t.setName("WebcastPushRealtime KeepAlive");
            t.start();

//            if (listener != null) {
//                listener.onOpen();
//            }
        }

//        public void sendMessage(String type, @Nullable JsonObject data) {
//            JsonObject payload = new JsonObject();
//
//            this.nonce++;
//
//            payload.put("type", type);
//            payload.put("nonce", String.valueOf(this.nonce));
//
//            if (data != null) {
//                payload.put("data", data);
//            }
//
//            FastLogger.logStatic(LogLevel.TRACE, "\u2191 %s", payload);
//            this.send(payload.toString());
//        }

        @Override
        public void onMessage(String message) {} // Unused.

        @SneakyThrows
        @Override
        public void onMessage(ByteBuffer bytes) {

            try {
                WebcastPushFrame frame = WebcastPushFrame.parseFrom(bytes);
                WebcastResponse resp = WebcastResponse.parseFrom(frame.getPayload());
                FastLogger.logStatic(LogLevel.TRACE, "\u2193 %s", resp);

                if (resp.getNeedsAck()) {
                    // Ignored.
                }

            } catch (Throwable t) {
                FastLogger.logStatic(LogLevel.SEVERE, "An uncaught exception occurred whilst processing frame:\n%s", t);
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            doCleanup();

//            if (listener != null) {
//                new Thread(() -> listener.onClose(remote));
//            }
        }

        @Override
        public void onError(Exception e) {
            FastLogger.logStatic(LogLevel.SEVERE, "An uncaught exception occurred:\n%s", e);
        }

    }

}
