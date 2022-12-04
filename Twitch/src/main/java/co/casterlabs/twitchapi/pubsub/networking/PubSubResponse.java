package co.casterlabs.twitchapi.pubsub.networking;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.twitchapi.TwitchApi;
import co.casterlabs.twitchapi.pubsub.PubSubError;
import co.casterlabs.twitchapi.pubsub.PubSubTopic;
import co.casterlabs.twitchapi.pubsub.messages.BitsV2TopicMessage;
import co.casterlabs.twitchapi.pubsub.messages.ChannelPointsV1TopicMessage;
import co.casterlabs.twitchapi.pubsub.messages.PubSubMessage;
import co.casterlabs.twitchapi.pubsub.messages.SubscriptionsV1TopicMessage;
import co.casterlabs.twitchapi.pubsub.messages.Unsupported_FollowingTopicMessage;
import co.casterlabs.twitchapi.pubsub.messages.Unsupported_VideoPlaybackTopicMessage;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class PubSubResponse {
    private IncomingMessageType type;

    private PubSubError error;
    private String nonce;

    private PubSubData data;

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class PubSubData {
        private String topic;
        private String message;

        @SneakyThrows
        public PubSubMessage deserializeMessage() {
            JsonObject message = TwitchApi.RSON.fromJson(this.message, JsonObject.class);

            try {
                switch (this.getTopicType()) {
                    case BITS_V2:
                        JsonObject bitsData = message.getObject("data");

                        bitsData.put("messageId", message.get("message_id"));

                        return TwitchApi.RSON.fromJson(bitsData, BitsV2TopicMessage.class);

                    case SUBSCRIPTIONS_V1:
                        return TwitchApi.RSON.fromJson(message, SubscriptionsV1TopicMessage.class);

                    case CHANNEL_POINTS_V1:
                        JsonObject pointsData = message.getObject("data");

                        return TwitchApi.RSON.fromJson(pointsData.get("redemption"), ChannelPointsV1TopicMessage.class);

                    case UNSUPPORTED_VIDEO_PLAYBACK:
                        return TwitchApi.RSON.fromJson(message, Unsupported_VideoPlaybackTopicMessage.class);

                    case UNSUPPORTED_FOLLOWING:
                        return TwitchApi.RSON.fromJson(message, Unsupported_FollowingTopicMessage.class);

                    default:
                        FastLogger.logStatic(LogLevel.DEBUG, "Unknown PubSub topic: \n%s", message);
                        return null;
                }
            } catch (JsonParseException e) {
                if (e.getMessage().equals("unsupported_ignore")) {
                    return null;
                } else {
                    throw e;
                }
            }
        }

        public PubSubTopic getTopicType() {
            for (PubSubTopic topic : PubSubTopic.values()) {
                if (this.topic.startsWith(topic.getTopic())) {
                    return topic;
                }
            }

            return null;
        }

    }

}
