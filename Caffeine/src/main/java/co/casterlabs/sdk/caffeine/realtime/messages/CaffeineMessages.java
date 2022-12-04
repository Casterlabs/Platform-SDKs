package co.casterlabs.sdk.caffeine.realtime.messages;

import java.io.Closeable;
import java.util.Base64;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.realtime.WSListener;
import co.casterlabs.sdk.caffeine.CaffeineApi;
import co.casterlabs.sdk.caffeine.CaffeineAuth;
import co.casterlabs.sdk.caffeine.CaffeineEndpoints;
import co.casterlabs.sdk.caffeine.realtime.ReaperWSConnection;
import co.casterlabs.sdk.caffeine.types.CaffeineProp;
import co.casterlabs.sdk.caffeine.types.CaffeineUser;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class CaffeineMessages implements Closeable {
    private @Setter @Nullable CaffeineMessagesListener listener;
    private @Setter @Nullable CaffeineAuth auth;

    private ReaperWSConnection conn;

    public CaffeineMessages(@NonNull CaffeineUser user) {
        this(user.getStageID());
    }

    public CaffeineMessages(@NonNull String caidOrStage) {
        if (caidOrStage.startsWith("CAID")) {
            // Convert to Stage ID.
            caidOrStage = caidOrStage.substring(4);
        }

        this.conn = new ReaperWSConnection(String.format(CaffeineEndpoints.CHAT, caidOrStage), new Listener());
    }

    public void connect() throws InterruptedException {
        this.conn.connect();
    }

    public boolean isOpen() {
        return this.conn.isOpen();
    }

    @Override
    public void close() {
        this.conn.close();
    }

    private class Listener implements WSListener {

        @Override
        public void onOpen() {
            JsonObject loginPayload = new JsonObject();
            JsonObject loginHeaders = new JsonObject();

            loginHeaders
                // .put("X-Caffeine-Vendor-Identifier", "d3370eb6-2e83-4355-b31b-eef0a6813a15")
                // .put("X-Client-Version", "1.2.4")
                .put("X-Client-Type", "web");

            if (auth == null) {
                loginHeaders
                    .put("Authorization", "Anonymous ANON-Fish")
                    .put("X-Credential", CaffeineAuth.getAnonymousCredential());
            } else {
                loginHeaders
                    .put("Authorization", "Bearer " + auth.getAccessToken())
                    .put("X-Credential", auth.getCredential());

                loginPayload.put(
                    "Body",
                    new JsonObject()
                        .put("user", auth.getSignedToken())
                        .toString()
                );
            }

            conn.send(
                loginPayload
                    .put("Headers", loginHeaders)
                    .toString()
            );

            if (listener != null) {
                listener.onOpen();
            }
        }

        @SneakyThrows
        @Override
        public void onMessage(String raw) {
            JsonObject json = CaffeineApi.RSON.fromJson(raw, JsonObject.class);

            if (json.containsKey("Compatibility-Mode") || json.containsKey("Status")) {
                return; // Some weird handshake success.
            }

            if (!json.containsKey("type")) {
                FastLogger.logStatic(LogLevel.WARNING, "Unknown payload:\n%s", json);
                return;
            }

            if (listener == null) {
                return; // User hasn't added a listener yet, it's not worth processing.
            }

            CaffeineAlertType type = CaffeineAlertType.fromJson(json.get("type"));

            if (type == CaffeineAlertType.UNKNOWN) {
                return;
            }

            CaffeineUser sender = CaffeineUser.fromJson(json.getObject("publisher"));
            JsonObject body = json.getObject("body");
            String id = getMessageId(json.get("id").getAsString());

            switch (type) {
                case REACTION:
                    ChatEvent chatEvent = new ChatEvent(sender, body.get("text").getAsString(), id);

                    if (json.containsKey("endorsement_count")) {
                        listener.onUpvote((new UpvoteEvent(chatEvent, json.getNumber("endorsement_count").intValue())));
                    } else {
                        listener.onChat(chatEvent);
                    }

                    return;

                case SHARE:
                    ShareEvent shareEvent = new ShareEvent(sender, body.get("text").getAsString(), id);

                    if (json.containsKey("endorsement_count")) {
                        listener.onUpvote((new UpvoteEvent(shareEvent, json.getNumber("endorsement_count").intValue())));
                    } else {
                        listener.onShare(shareEvent);
                    }

                    return;

                case DIGITAL_ITEM:
                    JsonObject propJson = body.getObject("digital_item");
                    CaffeineProp prop = CaffeineProp.fromJson(propJson);
                    PropEvent propEvent = new PropEvent(sender, body.get("text").getAsString(), id, propJson.getNumber("count").intValue(), prop);

                    if (json.containsKey("endorsement_count")) {
                        listener.onUpvote((new UpvoteEvent(propEvent, json.getNumber("endorsement_count").intValue())));
                    } else {
                        listener.onProp(propEvent);
                    }

                    return;

                case FOLLOW:
                    listener.onFollow(new FollowEvent(sender));

                    return;

                case UNKNOWN:
                    return;
            }
        }

        @Override
        public void onClose() {
            listener.onClose();
        }

    }

    @SneakyThrows
    private static String getMessageId(String b64) {
        byte[] bytes = Base64.getDecoder().decode(b64);
        JsonObject json = CaffeineApi.RSON.fromJson(new String(bytes), JsonObject.class);

        return json.get("u").getAsString();
    }

}
