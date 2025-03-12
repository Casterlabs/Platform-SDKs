package co.casterlabs.sdk.kick.unsupported.realtime;

import java.io.Closeable;

import com.pusher.client.AuthorizationFailureException;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickApi;
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickAuth;
import co.casterlabs.sdk.kick.unsupported.requests.UnsupportedKickGetPusherTokenRequest;
import lombok.Getter;
import lombok.NonNull;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;

public class UnsupportedKickPrivateChannelRealtime implements Closeable {
    private final FastLogger logger;
    private final UnsupportedKickPrivateChannelListener listener;

    private final @Getter long channelId;
    private final UnsupportedKickAuth auth;

    private Pusher pusher;
    private Thread holdThread;

    public UnsupportedKickPrivateChannelRealtime(long channelId, @NonNull UnsupportedKickAuth auth, @NonNull UnsupportedKickPrivateChannelListener listener) {
        this.channelId = channelId;
        this.auth = auth;
        this.listener = listener;
        this.logger = new FastLogger("Kick Private Channel Realtime: " + this.channelId);
    }

    public void connect() {
        assert this.pusher == null : "You must close the connection before reconnecting.";

        PusherOptions options = new PusherOptions()
            .setChannelAuthorizer((String channelName, String socketId) -> {
                if (!channelName.startsWith("private-")) return "";

                this.logger.debug("Authenticating for %s/%s", channelName, socketId);
                try {
                    String pusherToken = new UnsupportedKickGetPusherTokenRequest(this.auth)
                        .setPusherChannel(channelName)
                        .setSocketId(socketId)
                        .send();
                    this.logger.debug("Successfully authenticated with Pusher!");
                    return pusherToken;
                } catch (ApiException e) {
                    this.logger.severe("Failed to authenticate with Pusher:\n%s", e);
                    throw new AuthorizationFailureException(e);
                }
            })
            .setCluster(UnsupportedKickApi.PUSHER_CLUSTER);
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

        this.pusher.subscribePrivate("private-channel." + this.channelId)
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

                case "App\\Events\\StartStream":
                case "App\\Events\\StopStreamBroadcast":
                    return;

                case "App\\Events\\FollowersUpdatedForChannelOwner": {
                    JsonObject json = Rson.DEFAULT.fromJson(data, JsonObject.class);
                    this.listener.onFollow(json.getString("username"), json.getBoolean("followed"));
                    return;
                }

                case "App\\Events\\NewActivityFeedEvent": {
                    JsonObject json = Rson.DEFAULT.fromJson(data, JsonObject.class);

                    switch (json.getString("type")) {
                        case "new_subscriber":
                            this.listener.onSubscription(json.getString("username"), 1);
                            return;

                        case "gift":
                            this.listener.onGiftSubscriptions(
                                json.getString("username"),
                                json.getObject("metadata").getNumber("quantity").intValue()
                            );
                            return;

                        default:
                            this.logger.warn("Unrecognized NewActivityFeedEvent type: %s %s", type, data);
                            return;
                    }
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
