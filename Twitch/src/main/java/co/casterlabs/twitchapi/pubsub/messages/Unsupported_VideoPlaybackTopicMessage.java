package co.casterlabs.twitchapi.pubsub.messages;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.twitchapi.pubsub.PubSubTopic;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class Unsupported_VideoPlaybackTopicMessage implements PubSubMessage {
    private @JsonExclude VideoPlaybackMessageType subtype;

    @JsonField("viewers")
    private int viewcount_viewers = -1;

    @JsonDeserializationMethod("type")
    private void $deserialize_type(JsonElement e) throws JsonParseException {
        String type = e.getAsString().replace('-', '_').toUpperCase();

        try {
            this.subtype = VideoPlaybackMessageType.valueOf(type);
        } catch (IllegalArgumentException ignored) {
            throw new JsonParseException("unsupported_ignore");
        }
    }

    @Override
    public PubSubTopic getType() {
        return PubSubTopic.UNSUPPORTED_VIDEO_PLAYBACK;
    }

    public static enum VideoPlaybackMessageType {
        VIEWCOUNT,
        STREAM_UP,
        STREAM_DOWN;
    }

}
