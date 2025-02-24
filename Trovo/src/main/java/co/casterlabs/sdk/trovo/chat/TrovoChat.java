package co.casterlabs.sdk.trovo.chat;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.realtime.WSConnection;
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

public class TrovoChat extends WSConnection {
    private static final long KEEPALIVE = TimeUnit.SECONDS.toMillis(20);
    private static final URI CHAT_URI = URI.create("wss://open-chat.trovo.live/chat");

    private @Setter @Nullable ChatListener listener;

    private JsonObject auth;
    private long nonce = 0;

    public TrovoChat(@NonNull TrovoAuth auth) throws ApiException, ApiAuthException, IOException {
        if (auth.isApplicationAuth()) {
            throw new ApiAuthException("Cannot use application auth.");
        }

        this.auth = auth.getChatToken();
        this.setUri(CHAT_URI);
    }

    private void sendMessage(String type, @Nullable JsonObject data) throws IOException {
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
    protected void onOpen() {
        this.nonce = 0;
        this.sendMessage("AUTH", this.auth);

        this.doKeepAlive(KEEPALIVE, () -> {
            try {
                this.sendMessage("PING", null);
            } catch (IOException ignored) {}
        });

        if (this.listener != null) {
            this.listener.onOpen();
        }
    }

    @Override
    protected void onMessage(String raw) {
        try {
            JsonObject message = Rson.DEFAULT.fromJson(raw, JsonObject.class);
            String type = message.get("type").getAsString();

            switch (type) {
                case "CHAT": {
                    if (this.listener == null) return;

                    JsonObject data = message.getObject("data");
                    if (!data.containsKey("chats") || data.get("chats").isJsonNull()) {
                        return;
                    }

                    JsonArray chats = data.getArray("chats");
                    boolean isCatchup = chats.size() > 1;

                    for (JsonElement e : chats) {
                        TrovoMessage chat = parseChat(e.getAsObject(), isCatchup);

                        if (chat != null) {
                            fireListener(chat, listener);
                        }
                    }
                    return;
                }
            }
        } catch (Throwable t) {
            FastLogger.logStatic(LogLevel.SEVERE, "An uncaught exception occurred whilst processing frame: %s\n%s", raw, t);
        }
    }

    @Override
    protected void onClose(boolean remote) {
        if (listener != null) {
            listener.onClose(remote);
        }
    }

    private static void fireListener(TrovoMessage chat, ChatListener listener) {
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
    private static TrovoMessage parseChat(JsonObject chat, boolean isCatchup) {
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

}
