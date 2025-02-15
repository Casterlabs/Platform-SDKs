package co.casterlabs.sdk.twitch.helix.types;

import java.lang.reflect.Field;
import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class HelixEventSubSubscription {
    public final String id = null;
    public final String type = null;
    public final String version = null;
    public final Integer cost = null;
    public final JsonObject condition = null;
    public final HelixEventSubTransport transport = null;

    @JsonField("created_at")
    public final Instant createdAt = null;

    public final @JsonExclude HelixEventSubStatus status = null;

    @JsonDeserializationMethod("status")
    private void $deserialize_status(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        HelixEventSubStatus status = HelixEventSubStatus.valueOf(e.getAsString().toUpperCase());

        Field f = HelixEventSubSubscription.class.getField("status");
        f.setAccessible(true);
        f.set(this, status);
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class HelixEventSubTransport {
        public final @JsonExclude HelixEventSubTransportMethod method = null;
        public final String callback = null;

        @JsonField("session_id")
        public final String sessionId = null;

        @JsonField("connected_at")
        public final Instant connectedAt = null;

        @JsonField("disconnected_at")
        public final Instant disconnectedAt = null;

        @JsonDeserializationMethod("method")
        private void $deserialize_method(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
            HelixEventSubTransportMethod status = HelixEventSubTransportMethod.valueOf(e.getAsString().toUpperCase());

            Field f = HelixEventSubTransport.class.getField("method");
            f.setAccessible(true);
            f.set(this, status);
        }

        public static enum HelixEventSubTransportMethod {
            WEBHOOK,
            WEBSOCKET
        }

    }

    public static enum HelixEventSubStatus {
        /**
         * The subscription is enabled.
         */
        ENABLED,

        /**
         * The subscription is pending verification of the specified callback URL.
         */
        WEBHOOK_CALLBACK_VERIFICATION_PENDING,

        /**
         * The specified callback URL failed verification.
         */
        WEBHOOK_CALLBACK_VERIFICATION_FAILED,

        /**
         * The notification delivery failure rate was too high.
         */
        NOTIFICATION_FAILURES_EXCEEDED,

        /**
         * The authorization was revoked for one or more users specified in the
         * Condition object.
         */
        AUTHORIZATION_REVOKED,

        /**
         * The moderator that authorized the subscription is no longer one of the
         * broadcaster's moderators.
         */
        MODERATOR_REMOVED,

        /**
         * One of the users specified in the Condition object was removed.
         */
        USER_REMOVED,

        /**
         * The user specified in the Condition object was banned from the broadcaster's
         * chat.
         */
        CHAT_USER_BANNED,

        /**
         * The subscription to subscription type and version is no longer supported.
         */
        VERSION_REMOVED,

        /**
         * The subscription to the beta subscription type was removed due to
         * maintenance.
         */
        BETA_MAINTENANCE,

        /**
         * The client closed the connection.
         */
        WEBSOCKET_DISCONNECTED,

        /**
         * The client failed to respond to a ping message.
         */
        WEBSOCKET_FAILED_PING_PONG,

        /**
         * The client sent a non-pong message. Clients may only send pong messages (and
         * only in response to a ping message).
         */
        WEBSOCKET_RECEIVED_INBOUND_TRAFFIC,

        /**
         * The client failed to subscribe to events within the required time.
         */
        WEBSOCKET_CONNECTION_UNUSED,

        /**
         * The Twitch WebSocket server experienced an unexpected error.
         */
        WEBSOCKET_INTERNAL_ERROR,

        /**
         * The Twitch WebSocket server timed out writing the message to the client.
         */
        WEBSOCKET_NETWORK_TIMEOUT,

        /**
         * The Twitch WebSocket server experienced a network error writing the message
         * to the client.
         */
        WEBSOCKET_NETWORK_ERROR,

        /**
         * The client failed to reconnect to the Twitch WebSocket server within the
         * required time after a Reconnect Message.
         */
        WEBSOCKET_FAILED_TO_RECONNECT,
    }

}
