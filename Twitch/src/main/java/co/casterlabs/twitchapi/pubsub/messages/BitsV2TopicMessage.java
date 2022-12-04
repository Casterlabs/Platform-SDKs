package co.casterlabs.twitchapi.pubsub.messages;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.twitchapi.pubsub.PubSubTopic;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class BitsV2TopicMessage implements PubSubMessage {
    @Nullable
    @JsonField("user_name")
    private String username;

    @Nullable
    @JsonField("user_id")
    private String userId;

    @Nullable
    @JsonField("channel_name")
    private String channelname;

    @JsonField("channel_id")
    private String channelId;

    @JsonField("chat_message")
    private String chatMessage;

    @JsonField("bits_used")
    private int bitsUsed;

    @JsonField("total_bits_used")
    private int totalBitsUsed;

    private String context;

    // Injected in the parser.
    private String messageId;

    public boolean isAnonymous() {
        return this.userId == null;
    }

    @Override
    public PubSubTopic getType() {
        return PubSubTopic.BITS_V2;
    }

}
