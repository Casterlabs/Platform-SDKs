package co.casterlabs.sdk.trovo.chat;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.trovo.TrovoAuth;
import co.casterlabs.sdk.trovo.chat.messages.TrovoChatMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoCustomSpellMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoFollowMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoGiftSubMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoGiftSubRandomlyMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoMagicChatMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoPlatformEventMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoRaidWelcomeMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoSpellMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoSubscriptionMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoWelcomeMessage;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class TrovoChat implements Closeable {
    private static final long KEEPALIVE = TimeUnit.SECONDS.toMillis(20);
    private static URI CHAT_URI;

    private @Setter @Nullable ChatListener listener;

    private Connection conn;
    private JsonObject auth;

    static {
        try {
            CHAT_URI = new URI("wss://open-chat.trovo.live/chat");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public TrovoChat(@NonNull TrovoAuth auth) throws ApiException, ApiAuthException, IOException {
        if (auth.isApplicationAuth()) {
            throw new ApiAuthException("Cannot use application auth.");
        }

        this.auth = auth.getChatToken();
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
        private long nonce = 0;

        public Connection() {
            super(CHAT_URI);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            this.sendMessage("AUTH", auth);

            Thread t = new Thread(() -> {
                while (this.isOpen()) {
                    try {
                        this.sendMessage("PING", null);
                        Thread.sleep(KEEPALIVE);
                    } catch (Exception ignored) {}
                }
            });

            t.setName("TrovoChat KeepAlive");
            t.start();

            if (listener != null) {
                listener.onOpen();
            }
        }

        public void sendMessage(String type, @Nullable JsonObject data) {
            JsonObject payload = new JsonObject();

            this.nonce++;

            payload.put("type", type);
            payload.put("nonce", String.valueOf(this.nonce));

            if (data != null) {
                payload.put("data", data);
            }

            FastLogger.logStatic(LogLevel.TRACE, "\u2191 %s", payload);
            this.send(payload.toString());
        }

        @SneakyThrows
        @Override
        public void onMessage(String raw) {
            FastLogger.logStatic(LogLevel.TRACE, "\u2193 %s", raw);

            try {
                JsonObject message = Rson.DEFAULT.fromJson(raw, JsonObject.class);
                String type = message.get("type").getAsString();

                if (type.equals("CHAT")) {
                    if (listener != null) {
                        JsonObject data = message.getObject("data");

                        if (!data.containsKey("chats") || data.get("chats").isJsonNull()) {
                            return;
                        }

                        JsonArray chats = data.getArray("chats");
                        boolean isCatchup = chats.size() > 1;

                        for (JsonElement e : chats) {
                            TrovoMessage chat = this.parseChat(e.getAsObject(), isCatchup);

                            if (chat != null) {
                                this.fireListener(chat);
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                FastLogger.logStatic(LogLevel.SEVERE, "An uncaught exception occurred whilst processing frame: %s\n%s", raw, t);
            }
        }

        private void fireListener(TrovoMessage chat) {
            switch (chat.getType()) {
                case CHAT:
                    listener.onChatMessage((TrovoChatMessage) chat);
                    break;

                case FOLLOW:
                    listener.onFollow((TrovoFollowMessage) chat);
                    break;

                case GIFT_SUB_RANDOM:
                    listener.onGiftSubRandomly((TrovoGiftSubRandomlyMessage) chat);
                    break;

                case GIFT_SUB_USER:
                    listener.onGiftSub((TrovoGiftSubMessage) chat);
                    break;

                case MAGIC_CHAT_BULLET_SCREEN:
                case MAGIC_CHAT_COLORFUL:
                case MAGIC_CHAT_SPELL:
                case MAGIC_CHAT_SUPER_CAP:
                    listener.onMagicChat((TrovoMagicChatMessage) chat);
                    break;

                case SPELL:
                    listener.onSpell((TrovoSpellMessage) chat);
                    break;

                case PLATFORM_EVENT:
                    listener.onPlatformEvent((TrovoPlatformEventMessage) chat);
                    break;

                case RAID_WELCOME:
                    listener.onRaidWelcome((TrovoRaidWelcomeMessage) chat);
                    break;

                case SUBSCRIPTION:
                    listener.onSubscription((TrovoSubscriptionMessage) chat);
                    break;

                case WELCOME:
                    listener.onWelcome((TrovoWelcomeMessage) chat);
                    break;

                case CUSTOM_SPELL:
                    listener.onCustomSpell((TrovoCustomSpellMessage) chat);
                    break;

                case UNKNOWN:
                    break;
            }
        }

        @SneakyThrows
        public TrovoMessage parseChat(JsonObject chat, boolean isCatchup) {
            TrovoRawChatMessage raw = Rson.DEFAULT.fromJson(chat, TrovoRawChatMessage.class);
            TrovoMessageType type = TrovoMessageType.lookup(raw.type);

            raw.is_catchup = isCatchup;

            if ((raw.avatar != null) && !raw.avatar.contains("://")) {
                raw.avatar = "https://headicon.trovo.live/user/" + raw.avatar; // Damn you Trovo.
            }

            switch (type) {
                case CHAT:
                    return new TrovoChatMessage(raw);

                case FOLLOW:
                    return new TrovoFollowMessage(raw);

                case GIFT_SUB_RANDOM:
                    return new TrovoGiftSubRandomlyMessage(raw);

                case GIFT_SUB_USER:
                    return new TrovoGiftSubMessage(raw);

                case MAGIC_CHAT_BULLET_SCREEN:
                case MAGIC_CHAT_COLORFUL:
                case MAGIC_CHAT_SPELL:
                case MAGIC_CHAT_SUPER_CAP:
                    return new TrovoMagicChatMessage(raw, type);

                case PLATFORM_EVENT:
                    return new TrovoPlatformEventMessage(raw);

                case RAID_WELCOME:
                    return new TrovoRaidWelcomeMessage(raw);

                case SPELL:
                    return new TrovoSpellMessage(raw, Rson.DEFAULT.fromJson(raw.content, JsonObject.class));

                case SUBSCRIPTION:
                    return new TrovoSubscriptionMessage(raw);

                case WELCOME:
                    return new TrovoWelcomeMessage(raw);

                case CUSTOM_SPELL:
                    return new TrovoCustomSpellMessage(raw, Rson.DEFAULT.fromJson(raw.content, JsonObject.class));

                case UNKNOWN:
                default:
                    return null;

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
