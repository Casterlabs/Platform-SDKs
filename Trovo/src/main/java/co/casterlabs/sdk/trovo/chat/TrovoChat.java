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
import co.casterlabs.sdk.trovo.chat.messages.TrovoMessage;
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
                        TrovoMessage chat = TrovoMessage.parse(e.getAsObject(), isCatchup);
                        if (chat == null) continue;

                        ChatListener.fireListener(chat, this.listener);
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

}
