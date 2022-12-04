package co.casterlabs.twitchapi.helix.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.twitchapi.pubsub.messages.SubscriptionsV1TopicMessage.SubscriptionPlan;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class HelixSubscriber {
    @JsonField("is_gift")
    private boolean gift;

    private SubscriptionPlan tier;

    @JsonField("plan_name")
    private String planName;

    @JsonField("user_login")
    private String login;

    @JsonField("user_name")
    private String displayname;

    @JsonField("user_id")
    private String userId;

}
