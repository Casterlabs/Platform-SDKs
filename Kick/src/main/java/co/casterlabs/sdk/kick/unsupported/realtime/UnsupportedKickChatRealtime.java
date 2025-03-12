package co.casterlabs.sdk.kick.unsupported.realtime;

import java.io.Closeable;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickApi;
import co.casterlabs.sdk.kick.unsupported.realtime.types.UnsupportedKickChatEvent;
import co.casterlabs.sdk.kick.unsupported.realtime.types.UnsupportedKickPollUpdateEvent;
import co.casterlabs.sdk.kick.unsupported.realtime.types.UnsupportedKickRaidEvent;
import lombok.Getter;
import lombok.NonNull;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;

public class UnsupportedKickChatRealtime implements Closeable {
    private final FastLogger logger;
    private final UnsupportedKickChatListener listener;

    private final @Getter long chatRoomId;

    private Pusher pusher;
    private Thread holdThread;

    public UnsupportedKickChatRealtime(long chatRoomId, @NonNull UnsupportedKickChatListener listener) {
        this.chatRoomId = chatRoomId;
        this.listener = listener;
        this.logger = new FastLogger("Kick Chat Realtime: " + this.chatRoomId);
    }

    public void connect() {
        assert this.pusher == null : "You must close the connection before reconnecting.";

        PusherOptions options = new PusherOptions().setCluster(UnsupportedKickApi.PUSHER_CLUSTER);
        this.pusher = new Pusher(UnsupportedKickApi.PUSHER_KEY, options);

        this.pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                logger.debug("Connection state: %s", change.getCurrentState());
                switch (change.getCurrentState()) {
                    case CONNECTED:
                        listener.onOpen();
                        break;

                    case DISCONNECTED:
                        holdThread.interrupt();
                        listener.onClose();
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onError(String message, String code, Exception e) {
                logger.severe("Connection error: %s %s\n%s", code, message, e);
            }
        }, ConnectionState.ALL);

        this.pusher.subscribe("chatrooms." + this.chatRoomId + ".v2")
            .bindGlobal(this::onEvent);

        this.holdThread = new Thread(() -> {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        this.holdThread.start();
    }

    private void onEvent(PusherEvent event) {
        String type = event.getEventName();
        String data = event.getData();
        this.logger.debug("%s: %s", type, data);

        try {
            switch (type) {
                case "App\\Events\\ChatMessageEvent":
                    this.listener.onChat(
                        Rson.DEFAULT.fromJson(data, UnsupportedKickChatEvent.class)
                    );
                    return;

                case "App\\Events\\SubscriptionEvent": {
                    JsonObject json = Rson.DEFAULT.fromJson(data, JsonObject.class);
                    this.listener.onSubscription(json.getString("username"), json.getNumber("months").intValue());
                    return;
                }

                case "App\\Events\\GiftedSubscriptionsEvent": {
                    JsonObject json = Rson.DEFAULT.fromJson(data, JsonObject.class);
                    String gifter = json.getString("gifter_username");
                    String[] giftRecipients = Rson.DEFAULT.fromJson(json.get("gifted_usernames"), String[].class);

                    this.listener.onGiftSubscriptions(giftRecipients, gifter);
                    return;
                }

                case "App\\Events\\MessageDeletedEvent": {
                    JsonObject json = Rson.DEFAULT.fromJson(data, JsonObject.class);
                    this.listener.onDeleted(json.getObject("message").getString("id"));
                    return;
                }

                case "App\\Events\\ChatroomClearEvent":
                    this.listener.onClear();
                    return;

                case "App\\Events\\StreamHostEvent":
                    this.listener.onRaid(
                        Rson.DEFAULT.fromJson(data, UnsupportedKickRaidEvent.class)
                    );
                    return;

                case "App\\Events\\PollUpdateEvent": {
                    JsonObject json = Rson.DEFAULT.fromJson(data, JsonObject.class);
                    this.listener.onPollUpdate(
                        Rson.DEFAULT.fromJson(json.get("poll"), UnsupportedKickPollUpdateEvent.class)
                    );
                    return;
                }

                case "App\\Events\\PollDeleteEvent":
                    this.listener.onPollDelete();
                    return;

                case "App\\Events\\UserBannedEvent": {
                    JsonObject json = Rson.DEFAULT.fromJson(data, JsonObject.class);
                    this.listener.onUserBanned(json.getObject("user").getString("slug"));
                    return;
                }

                default:
                    this.logger.warn("Unrecognized type: %s %s", type, data);
                    return;
            }
        } catch (Throwable e) {
            this.logger.severe("An error occurred whilst handling event: %s %s\n%s", type, data, e);
        }
    }

    public boolean isOpen() {
        return this.pusher != null;
    }

    @Override
    public void close() {
        if (this.pusher == null) return;

        try {
            this.pusher.disconnect();
        } catch (Exception ignored) {} finally {
            this.pusher = null;
        }
    }

}
