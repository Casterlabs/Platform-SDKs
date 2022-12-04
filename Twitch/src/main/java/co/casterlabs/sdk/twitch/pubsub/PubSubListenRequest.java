package co.casterlabs.sdk.twitch.pubsub;

import java.util.ArrayList;
import java.util.List;

import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import co.casterlabs.sdk.twitch.pubsub.networking.OutgoingMessageType;
import co.casterlabs.sdk.twitch.pubsub.networking.Packet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@RequiredArgsConstructor
public class PubSubListenRequest extends Packet<OutgoingMessageType> {
    private List<String> topics = new ArrayList<>();
    private @Setter boolean unlistenMode = false;
    private @Setter boolean sealed = false;

    private @NonNull TwitchHelixAuth auth;
    private @NonNull PubSubListener listener;

    public PubSubListenRequest addTopic(@NonNull PubSubTopic topic, @NonNull String userId) {
        if (this.sealed) {
            throw new IllegalStateException("Request is sealed, create another.");
        } else {
            this.topics.add(topic.getTopic() + "." + userId);

            return this;
        }
    }

    @Override
    public OutgoingMessageType getPacketType() {
        return this.unlistenMode ? OutgoingMessageType.UNLISTEN : OutgoingMessageType.LISTEN;
    }

    @SneakyThrows
    @Override
    public JsonObject serialize() {
        this.sealed = true;

        JsonObject json = new JsonObject();
        JsonObject data = new JsonObject();
        JsonArray topics = new JsonArray();

        for (String topic : this.topics) {
            topics.add(topic);
        }

        data.put("auth_token", this.auth.getAccessToken());
        data.put("topics", topics);

        json.put("type", this.getPacketType().name());
        json.put("nonce", this.topics.get(0));
        json.put("data", data);

        return json;
    }

}
