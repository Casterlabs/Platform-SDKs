package co.casterlabs.glimeshapijava.realtime;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.jetbrains.annotations.Nullable;

import co.casterlabs.commons.async.AsyncTask;
import co.casterlabs.glimeshapijava.GlimeshApiJava;
import co.casterlabs.glimeshapijava.GlimeshAuth;
import co.casterlabs.glimeshapijava.types.GlimeshChannel;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.NonNull;
import lombok.Setter;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class GlimeshRealtimeChannel implements Closeable {
    private static final String SUBSCRIBE_CHANNEL = "subscription{channel(id: \"%s\"){" + GlimeshChannel.GQL_DATA + "}}";

    private @Setter @Nullable GlimeshChannelListener listener;

    private GlimeshAuth auth;
    private String channelId;

    private Connection conn;

    public GlimeshRealtimeChannel(@NonNull GlimeshAuth auth, @NonNull GlimeshChannel channel) {
        this(auth, channel.getId());
    }

    @Deprecated
    public GlimeshRealtimeChannel(@NonNull GlimeshAuth auth, String channelId) {
        this.auth = auth;
        this.channelId = channelId;
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
            super(auth.getRealtimeUrl());
        }

        @Override
        public void send(String payload) {
            FastLogger.logStatic(LogLevel.TRACE, GlimeshRealtimeHelper.DEBUG_WS_SEND, payload);
            super.send(payload);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            this.send(GlimeshRealtimeHelper.PHX_JOIN);

            String subscribe = String.format(SUBSCRIBE_CHANNEL, channelId);

            this.send(String.format(GlimeshRealtimeHelper.PHX_DOC, GlimeshApiJava.formatQuery(subscribe)));

            AsyncTask.create(() -> {
                while (this.isOpen()) {
                    this.send(GlimeshRealtimeHelper.PHX_KA);
                    try {
                        Thread.sleep(TimeUnit.SECONDS.toMillis(30));
                    } catch (InterruptedException ignored) {}
                }
            });

            if (listener != null) {
                listener.onOpen();
            }
        }

        @Override
        public void onMessage(String raw) {
            FastLogger.logStatic(LogLevel.TRACE, GlimeshRealtimeHelper.DEBUG_WS_RECIEVE, raw);

            try {
                JsonArray phx = Rson.DEFAULT.fromJson(raw, JsonArray.class);

                String type = phx.get(3).getAsString();
                JsonObject payload = phx.getObject(4);

                switch (type) {
                    case "phx_reply": {
                        if (!payload.get("status").getAsString().equals("ok")) {
                            doCleanup();
                        }

                        break;
                    }

                    case "subscription:data": {
                        if (listener != null) {
                            GlimeshChannel channel = Rson.DEFAULT.fromJson(
                                payload
                                    .getObject("result")
                                    .getObject("data")
                                    .get("channel"),
                                GlimeshChannel.class
                            );

                            listener.onUpdate(channel);
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
