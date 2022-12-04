package co.casterlabs.twitchapi.pubsub;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;

import co.casterlabs.twitchapi.ThreadHelper;
import co.casterlabs.twitchapi.TwitchApi;
import co.casterlabs.twitchapi.pubsub.messages.PubSubMessage;
import co.casterlabs.twitchapi.pubsub.networking.PubSubResponse;
import lombok.NonNull;
import lombok.SneakyThrows;

public class PubSubRouter {
    private static final String PING_MESSAGE = "{\"type\": \"PING\"}";

    public static final int MAX_TOPICS = 50;

    private static URI PUBSUB_ENDPOINT;

    private Map<String, PubSubListener> listeners = new HashMap<>(MAX_TOPICS);
    private Connection connection = new Connection();

    static {
        try {
            PUBSUB_ENDPOINT = new URI("wss://pubsub-edge.twitch.tv");
        } catch (URISyntaxException ignored) {}
    }

    public int getAvailableSlots() {
        return MAX_TOPICS - this.listeners.size();
    }

    @SneakyThrows
    public synchronized void subscribeTopic(@NonNull PubSubListenRequest request) {
        if (this.getAvailableSlots() < request.getTopics().size()) {
            throw new IllegalStateException("Not enough slots, create another router.");
        } else {
            if (request.isUnlistenMode()) {
                throw new IllegalStateException("Request is not in listen mode.");
            } else {
                for (String topic : request.getTopics()) {
                    if (this.listeners.containsKey(topic)) {
                        throw new IllegalStateException("Topic is already registered: " + topic);
                    }
                }

                if (this.connection.getReadyState() == ReadyState.NOT_YET_CONNECTED) {
                    this.connection.connectBlocking();
                } else if (!this.isConnected()) {
                    this.connection.reconnectBlocking();
                }

                for (String topic : request.getTopics()) {
                    this.listeners.put(topic, request.getListener());
                }

                this.connection.send(request.serialize().toString());
            }
        }
    }

    public synchronized void unsubscribeTopic(@NonNull PubSubListenRequest request) {
        if (request.isUnlistenMode()) {
            if (this.isConnected()) {
                for (String topic : request.getTopics()) {
                    if (!this.listeners.containsKey(topic)) {
                        throw new IllegalStateException("Topic is not registered: " + topic);
                    }
                }

                for (String topic : request.getTopics()) {
                    this.listeners.remove(topic);
                }

                this.connection.send(request.serialize().toString());
            }
        } else {
            throw new IllegalStateException("Request is not in unlisten mode.");
        }
    }

    private boolean isConnected() {
        return !this.connection.isClosed();
    }

    private class Connection extends WebSocketClient {

        public Connection() {
            super(PUBSUB_ENDPOINT);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            ThreadHelper.executeAsync("Twitch PubSub KeepAlive", () -> {
                while (isConnected()) {
                    try {
                        TimeUnit.MINUTES.sleep(1);

                        this.send(PING_MESSAGE);
                    } catch (InterruptedException ignored) {}
                }
            });
        }

        @SneakyThrows
        @Override
        public void onMessage(String raw) {
            PubSubResponse response = TwitchApi.RSON.fromJson(raw, PubSubResponse.class);

            switch (response.getType()) {
                case MESSAGE:
                    PubSubListener messageListener = listeners.get(response.getData().getTopic());

                    if ((messageListener != null) && (response.getError() == null)) {
                        PubSubMessage message = response.getData().deserializeMessage();

                        if (message != null) {
                            messageListener.onMessage(message);
                        }
                    }
                    return;

                case RESPONSE:
                    PubSubListener listener = listeners.get(response.getNonce());

                    if ((listener != null) && (response.getError() != null)) {
                        listener.onError(response.getError());
                    }
                    return;

                case RESTART:
                    this.close();
                    return;

                case PONG: // Ignore.
                default:
                    return;

            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            ThreadHelper.executeAsync("Twitch PubSub Close Alerter", () -> {
                for (PubSubListener listener : new HashSet<>(listeners.values())) {
                    listener.onError(PubSubError.DISCONNECTED);
                }

                listeners.clear();
            });
        }

        @Override
        public void onError(Exception e) {
            e.printStackTrace();
        }

    }

}
