package co.casterlabs.sdk.twitch.pubsub.messages;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.sdk.twitch.pubsub.PubSubTopic;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class Unsupported_FollowingTopicMessage implements PubSubMessage {
    @JsonField("user_id")
    private String userId;

    private String username;

    @JsonField("display_name")
    private String displayname;

    @Override
    public PubSubTopic getType() {
        return PubSubTopic.UNSUPPORTED_FOLLOWING;
    }

}
