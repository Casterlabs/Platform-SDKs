package co.casterlabs.sdk.kick.realtime;

import java.io.Closeable;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.sdk.kick.KickApi;
import lombok.Getter;
import lombok.NonNull;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;

public class KickChannelRealtime implements Closeable {
    private final FastLogger logger;
    private final KickChannelListener listener;

    private final @Getter long channelId;

    private Pusher pusher;

    public KickChannelRealtime(long channelId, @NonNull KickChannelListener listener) {
        this.channelId = channelId;
        this.listener = listener;
        this.logger = new FastLogger("Kick Channel Realtime: " + this.channelId);
    }

    public void connect() {
        assert this.pusher == null : "You must close the connection before reconnecting.";

        PusherOptions options = new PusherOptions().setCluster(KickApi.PUSHER_CLUSTER);
        this.pusher = new Pusher(KickApi.PUSHER_KEY, options);

        this.pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                logger.debug("Connection state: %s", change.getCurrentState());
                switch (change.getCurrentState()) {
                    case CONNECTED:
                        listener.onOpen();
                        break;

                    case DISCONNECTED:
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

        this.pusher.subscribe("channel." + this.channelId)
            .bindGlobal(this::onEvent);
    }

    private void onEvent(PusherEvent event) {
        try {
            String type = event.getEventName();
            JsonObject data = Rson.DEFAULT.fromJson(event.getData(), JsonObject.class);
            this.logger.debug("%s: %s", type, data);

            switch (type) {
                case "App\\Events\\StreamerIsLive":
                    this.listener.onChannelLive(true);
                    return;

                case "App\\Events\\StopStreamBroadcast":
                    this.listener.onChannelLive(false);
                    return;

                default:
                    this.logger.warn("Unrecognized type: %s", type);
                    return;
            }
        } catch (JsonParseException e) {
            this.logger.exception(e);
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
