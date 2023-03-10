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
import co.casterlabs.sdk.kick.types.KickChannel;
import lombok.Getter;
import lombok.NonNull;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;

public class KickRealtime implements Closeable {
    private final FastLogger logger;
    private final @Getter long channelId;
    private final @Getter long chatId;

    private Pusher pusher;

    public KickRealtime(@NonNull KickChannel channel) {
        this.channelId = channel.getId();
        this.chatId = channel.getChatRoomId();
        this.logger = new FastLogger("Kick Realtime: " + this.channelId);
    }

    public void connect() {
        assert this.pusher == null : "You must close the connection before reconnecting.";

        PusherOptions options = new PusherOptions().setCluster(KickApi.PUSHER_CLUSTER);
        this.pusher = new Pusher(KickApi.PUSHER_KEY, options);

        this.pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                logger.debug("Connection state: %s", change.getCurrentState());
            }

            @Override
            public void onError(String message, String code, Exception e) {
                System.out.println("There was a problem connecting!");
                logger.debug("Connection error: %s %s\n%s", code, message, e);
            }
        }, ConnectionState.ALL);

        this.pusher.subscribe("channel." + this.channelId)
            .bindGlobal((PusherEvent event) -> {
                try {
                    String type = event.getEventName();
                    String data = event.getData();
                    this.onEvent(type, Rson.DEFAULT.fromJson(data, JsonObject.class));
                } catch (JsonParseException e) {
                    this.logger.exception(e);
                }
            });

        this.pusher.subscribe("chatrooms." + this.chatId)
            .bindGlobal((PusherEvent event) -> {
                try {
                    String type = event.getEventName();
                    String data = event.getData();
                    this.onEvent(type, Rson.DEFAULT.fromJson(data, JsonObject.class));
                } catch (JsonParseException e) {
                    this.logger.exception(e);
                }
            });
    }

    private void onEvent(String type, JsonObject data) {
        this.logger.debug("%s: %s", type, data);
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
