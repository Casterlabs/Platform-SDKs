package co.casterlabs.twitchapi.pubsub.messages;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.twitchapi.pubsub.PubSubTopic;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class SubscriptionsV1TopicMessage implements PubSubMessage {
    @Nullable
    @JsonField("user_name")
    private String username;

    @Nullable
    @JsonField("display_name")
    private String displayname;

    @Nullable
    @JsonField("user_id")
    private String userId;

    @JsonField("channel_name")
    private String channelname;

    @JsonField("channel_id")
    private String channelId;

    @JsonField("sub_plan")
    private SubscriptionPlan subPlan;

    @JsonField("sub_plan_name")
    private String planName;

    private SubscriptionContext context;

    @JsonField("multi_month_duration")
    private int monthDuration = 1;

    @JsonField("cumulative_months")
    private int cumulativeMonths;

    @JsonField("streak_months")
    private int streakMonths;

    @JsonField("is_gift")
    private boolean gift;

    @Nullable
    @JsonField("recipient_user_name")
    private String recipientUsername;

    @Nullable
    @JsonField("recipient_display_name")
    private String recipientDisplayname;

    @Nullable
    @JsonField("recipient_id")
    private String recipientUserId;

    @JsonField("sub_message")
    private SubMessage message;

    public boolean isAnonymous() {
        return this.userId == null;
    }

    @Override
    public PubSubTopic getType() {
        return PubSubTopic.SUBSCRIPTIONS_V1;
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class SubMessage {
        @Nullable
        private List<MessageEmote> emotes;
        private String message;

        public String getEmoteText(@NonNull MessageEmote emote) {
            return this.message.substring(emote.start, emote.end);
        }

    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class MessageEmote {
        private static final String EMOTE_LINK_BASE = "https://static-cdn.jtvnw.net/emoticons/v1/";

        private int start;
        private int end;
        private int id;

        public String getSmallImageLink() {
            return EMOTE_LINK_BASE + this.id + "/1.0";
        }

        public String getMediumImageLink() {
            return EMOTE_LINK_BASE + this.id + "/2.0";
        }

        public String getLargeImageLink() {
            return EMOTE_LINK_BASE + this.id + "/3.0";
        }

    }

    public static enum SubscriptionContext {
        SUB,
        RESUB,

        SUBGIFT,
        RESUBGIFT,

        ANONSUBGIFT,
        ANONRESUBGIFT;

    }

    public static enum SubscriptionPlan {
        UNKNOWN,
        PRIME,
        TIER_1,
        TIER_2,
        TIER_3;

    }

}
