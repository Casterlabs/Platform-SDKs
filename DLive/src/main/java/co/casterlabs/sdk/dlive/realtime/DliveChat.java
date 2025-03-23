package co.casterlabs.sdk.dlive.realtime;

import java.io.Closeable;
import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.element.JsonString;
import co.casterlabs.sdk.dlive.DliveApiJava;
import co.casterlabs.sdk.dlive.DliveAuth;
import co.casterlabs.sdk.dlive.realtime.events.DliveChatGift;
import co.casterlabs.sdk.dlive.realtime.events.DliveChatHost;
import co.casterlabs.sdk.dlive.realtime.events.DliveChatMessage;
import co.casterlabs.sdk.dlive.realtime.events.DliveChatSubscription;
import co.casterlabs.sdk.dlive.realtime.events._DliveChatDelete;
import co.casterlabs.sdk.dlive.realtime.events._DliveChatFollow;
import lombok.NonNull;
import lombok.Setter;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class DliveChat implements Closeable {
    private static final String DEBUG_WS_SEND = "\u2191 %s";
    private static final String DEBUG_WS_RECIEVE = "\u2193 %s";

    private Connection conn;
    private @Setter @Nullable DliveChatListener listener;

    private DliveAuth auth;
    private String username;

    public DliveChat(@NonNull DliveAuth auth, @NonNull String username) {
        this.auth = auth;
        this.username = username;
    }

    public void connect() {
        if (this.conn == null) {
            this.conn = new Connection();
            this.conn.connect();
        } else {
            throw new IllegalStateException("You must close the connection before reconnecting.");
        }
    }

    public void connectBlocking() throws InterruptedException {
        if (this.conn == null) {
            this.conn = new Connection();
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

        public Connection() {
            super(URI.create(DliveApiJava.REALTIME_API));
        }

        public void send(String type, JsonObject payload) {
            JsonObject frame = new JsonObject()
                .put("type", type)
                .put("payload", payload);

            FastLogger.logStatic(LogLevel.TRACE, DEBUG_WS_SEND, frame);
            super.send(frame.toString());
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            try {
                this.send("connection_init", JsonObject.singleton("authorization", auth.getAccessToken()));

                if (listener != null) {
                    listener.onOpen();
                }
            } catch (ApiAuthException e) {
                FastLogger.logException(e);
                this.close();
            }
        }

        @Override
        public void onMessage(String raw) {
            FastLogger.logStatic(LogLevel.TRACE, DEBUG_WS_RECIEVE, raw);

            try {
                JsonObject payload = Rson.DEFAULT.fromJson(raw, JsonObject.class);

                switch (payload.getString("type")) {
                    case "connection_ack": {
                        this.send(
                            "start",
                            JsonObject.singleton(
                                "query",
                                String.format("subscription{streamMessageReceived(streamer:%s){__typename}}", new JsonString(username))
                            )
                        );
                        break;
                    }

                    case "data": {
                        if (listener == null) break;

                        for (JsonElement e : payload.getObject("payload").getObject("data").getArray("streamMessageReceived")) {
                            JsonObject item = e.getAsObject();

                            // https://dev.dlive.tv/schema/chattype.doc.html
                            switch (item.getString("type")) {

                                case "Message": {
                                    listener.onMessage(Rson.DEFAULT.fromJson(item, DliveChatMessage.class));
                                    break;
                                }

                                case "Gift": {
                                    listener.onGift(Rson.DEFAULT.fromJson(item, DliveChatGift.class));
                                    break;
                                }

                                case "Live": {
                                    listener.onLive();
                                    break;
                                }

                                case "Offline": {
                                    listener.onOffline();
                                    break;
                                }

                                case "Follow": {
                                    listener.onFollow(Rson.DEFAULT.fromJson(item, _DliveChatFollow.class).sender);
                                    break;
                                }

                                case "Subscription": {
                                    listener.onSubscription(Rson.DEFAULT.fromJson(item, DliveChatSubscription.class));
                                    break;
                                }

                                case "Delete": {
                                    listener.onMessagesDelete(Rson.DEFAULT.fromJson(item, _DliveChatDelete.class).ids);
                                    break;
                                }

                                case "Host": {
                                    listener.onHost(Rson.DEFAULT.fromJson(item, DliveChatHost.class));
                                    break;
                                }

                                // Unused.
                                case "ChatMode":
                                case "Ban":
                                case "Mod":
                                case "Emote":
                                case "Timeout": {
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            } catch (Throwable t) {
                FastLogger.logStatic(LogLevel.SEVERE, "An uncaught exception occurred whilst processing frame: %s\n%s", raw, t);
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            doCleanup();

            if (listener != null) {
                new Thread(() -> listener.onClose(remote));
            }
        }

        @Override
        public void onError(Exception e) {
            FastLogger.logStatic(LogLevel.SEVERE, "An uncaught exception occurred:\n%s", e);
        }

    }

}
