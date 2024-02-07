package co.casterlabs.sdk.tiktok.unsupported.webcast.realtime;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.stream.Collectors;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.sdk.tiktok.unsupported.webcast.WebcastCookies;
import co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastPushFrame;
import co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastResponse;
import co.casterlabs.sdk.tiktok.unsupported.webcast.requests.WebcastGetIMFetchRequest;
import lombok.SneakyThrows;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class WebcastPushRealtime implements Closeable {
//    private @Setter @Nullable ChatListener listener;

    protected Connection conn;

    @SuppressWarnings("deprecation")
    protected void setup(String chatRoomId, WebcastCookies cookies) throws ApiAuthException, ApiException, IOException {
        WebcastResponse imData = new WebcastGetIMFetchRequest(cookies)
            .setRoomId(chatRoomId)
            .send();
        System.out.println(imData.getCursor());
        System.out.println(imData.getInternalExt());
        System.out.println(imData.getPushServer());
        System.out.println(imData.getRouteParamsMapMap());

        String uri = String.format(
            "%s?%s&cursor=%s&internal_ext=%s&live_id=12&roomid=%s&resp_content_type=protobuf&%s",
            imData.getPushServer(),
            cookies.getClientParams(),
            imData.getCursor(),
            URLEncoder.encode(imData.getInternalExt()),
            chatRoomId,
            imData
                .getRouteParamsMapMap()
                .entrySet()
                .stream()
                .map((entry) -> URLEncoder.encode(entry.getKey()) + '=' + URLEncoder.encode(entry.getValue()))
                .collect(Collectors.joining("&"))
        );
        System.out.println(uri);

        System.out.println(cookies.getCookie());

        this.conn = new Connection(uri, imData, cookies.getCookie());
    }

    public void connect(String chatRoomId, WebcastCookies cookies) throws ApiAuthException, ApiException, IOException {
        if (this.conn == null) {
            this.setup(chatRoomId, cookies);
            this.conn.connect();
        } else {
            throw new IllegalStateException("You must close the connection before reconnecting.");
        }
    }

    public void connectBlocking(String chatRoomId, WebcastCookies cookies) throws InterruptedException, ApiAuthException, ApiException, IOException {
        if (this.conn == null) {
            this.setup(chatRoomId, cookies);
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

    protected class Connection extends WebSocketClient {
        private WebcastResponse wr;

        public Connection(String uri, WebcastResponse wr, String cookie) {
            super(URI.create(uri), new Draft_6455(), Map.of("Cookie", cookie));
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

            t.setName("WebcastPushRealtime KeepAlive #" + this.hashCode());
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
                    this.send(
                        WebcastPushFrame.newBuilder()
                            .setPayloadType("ack")
                            .setLogId(frame.getLogId())
                            .setPayload(resp.getInternalExtBytes())
                            .build()
                            .toByteArray()
                    );
                }

            } catch (Throwable t) {
                FastLogger.logStatic(LogLevel.SEVERE, "An uncaught exception occurred whilst processing frame:\n%s", t);
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            FastLogger.logStatic(LogLevel.TRACE, "Closed!");
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
