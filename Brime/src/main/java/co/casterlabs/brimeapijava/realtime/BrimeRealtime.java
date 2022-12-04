package co.casterlabs.brimeapijava.realtime;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.BrimeAuth;
import co.casterlabs.brimeapijava.requests.BrimeGetAccountRequest;
import co.casterlabs.brimeapijava.types.BrimeChannel;
import co.casterlabs.brimeapijava.types.BrimeUser;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class BrimeRealtime implements Closeable {
    private static final String ENDPOINT = "wss://chat.brime.tv:8443/ws";

    // Connection
    private MqttConnectOptions options = new MqttConnectOptions();
    private IMqttClient publisher;

    private String channel;
    private @Nullable BrimeAuth auth;

    private @Setter @Nullable BrimeRealtimeListener listener;

    private boolean isConnecting = false;

    private @NonNull @Getter String language = "en-us";

    public void setLanguage(@NonNull String newLanguage) {
        newLanguage = newLanguage.toLowerCase();
        if (this.language.equals(newLanguage)) return; // NOOP

        if (this.isOpen()) {
            // Susbcribe on the new, unsub on the old.
            try {
                this.publisher.subscribe(String.format("channel/chat/receive/%s/%s", this.channel, newLanguage), this::handleMessage);
                this.publisher.unsubscribe(String.format("channel/chat/receive/%s/%s", this.channel, this.language));
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        this.language = newLanguage;
    }

    public BrimeRealtime(@NonNull BrimeChannel channel) throws IOException, ApiException {
        this(channel, null);
    }

    public BrimeRealtime(@NonNull BrimeChannel channel, @Nullable BrimeAuth auth) throws IOException, ApiAuthException, ApiException {
        try {
            this.channel = channel.getXid();
            this.auth = auth;

            if (this.auth == null) {
                String password = (String.valueOf(Math.random()) + String.valueOf(Math.random()))
                    .replace(".", "");

                this.options.setUserName("Guest");
                this.options.setPassword(password.toCharArray());
            } else {
                BrimeUser account = new BrimeGetAccountRequest(this.auth)
                    .send();

                this.options.setUserName(account.getXid());
            }

            this.publisher = new MqttClient(ENDPOINT, UUID.randomUUID().toString(), new MemoryPersistence());

            this.options.setCleanSession(true);
            this.options.setConnectionTimeout(4000);

            this.publisher.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    if (listener != null) {
                        listener.onClose(true);
                    }
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {}

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {}
            });

        } catch (MqttException e) {
            throw new IOException(e);
        }
    }

    public BrimeRealtime connect() throws IOException, ApiAuthException {
        if (this.isOpen()) {
            throw new IllegalStateException("You must close the connection before reconnecting.");
        } else {
            this.isConnecting = true;

            try {
                if (this.auth != null) {
                    // Fresh auth.
                    if (this.auth.isExpired()) {
                        this.auth.refresh();
                    }

                    this.options.setPassword(this.auth.getAccessToken().toCharArray());
                }

                this.publisher.connectWithResult(this.options);

                this.publisher.subscribe(String.format("channel/chat/receive/%s/%s", this.channel, this.language), this::handleMessage);
                this.publisher.subscribe(String.format("channel/%s/events", this.channel), this::handleMessage);

                if (this.listener != null) {
                    this.listener.onOpen();
                }

                return this;
            } catch (MqttException | InterruptedException e) {
                throw new IOException(e);
            } finally {
                this.isConnecting = false;
            }
        }
    }

    public void sendChatMessage(@NonNull String message) throws IOException {
        try {
            JsonObject payload = new JsonObject()
                .put("content", message)
                .put("reply_target", false);

            this.publisher.publish(
                "channel/chat/send/" + this.channel,
                payload
                    .toString()
                    .getBytes(StandardCharsets.UTF_8),
                0,
                false
            );
        } catch (MqttException e) {
            throw new IOException(e);
        }
    }

    public void sendChatMessageReply(@NonNull String message, @NonNull String replyTargetMessageXid) throws IOException {
        try {
            JsonObject payload = new JsonObject()
                .put("content", message)
                .put("reply_target", replyTargetMessageXid);

            this.publisher.publish(
                "channel/chat/send/" + this.channel,
                payload
                    .toString()
                    .getBytes(StandardCharsets.UTF_8),
                0,
                false
            );
        } catch (MqttException e) {
            throw new IOException(e);
        }
    }

    public boolean isOpen() {
        return this.isConnecting || this.publisher.isConnected();
    }

    @Override
    public void close() throws IOException {
        try {
            this.publisher.disconnect();
        } catch (MqttException e) {
            throw new IOException(e);
        } finally {
            if (this.listener != null) {
                this.listener.onClose(false);
            }
        }
    }

    private void handleMessage(String topic, MqttMessage message) {
        if (this.listener == null) return;

        try {
            String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
            JsonObject jsonPayload = BrimeApi.RSON.fromJson(payload, JsonObject.class);

            String type;

            if (jsonPayload.containsKey("type")) {
                type = jsonPayload.getString("type");
            } else if (jsonPayload.containsKey("event")) {
                type = jsonPayload.getString("event");
            } else {
                BrimeChatMessage chatMessage = BrimeApi.RSON.fromJson(jsonPayload, BrimeChatMessage.class);

                this.listener.onChat(chatMessage);
                return;
            }

            switch (type) {
                case "delete": {
                    String xid = jsonPayload.getString("targetMsg");
                    this.listener.onChatMessageDelete(xid);
                    break;
                }

                case "clear": {
                    this.listener.onChatClear();
                    break;
                }

                case "live": {
                    this.listener.onChannelWentLive();
                    break;
                }

                case "offline": {
                    this.listener.onChannelWentOffline();
                    break;
                }

                case "viewer_join": {
                    String xid = jsonPayload.getObject("viewer").getString("xid");
                    this.listener.onViewerJoin(xid);
                    break;
                }

                case "viewer_leave": {
                    String xid = jsonPayload.getObject("viewer").getString("xid");
                    this.listener.onViewerLeave(xid);
                    break;
                }

            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
    }

}
