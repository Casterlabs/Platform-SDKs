package co.casterlabs.twitchapi.pubsub.messages;

import java.time.Instant;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.twitchapi.pubsub.PubSubTopic;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class ChannelPointsV1TopicMessage implements PubSubMessage {

    @JsonField("channel_id")
    private String channelId;

    private ChannelPointsRedemptionStatus status;

    private String id;

    @Nullable
    @JsonField("user_input")
    private String userInput;

    private ChannelPointsUser user;

    private ChannelPointsReward reward;

    @Override
    public PubSubTopic getType() {
        return PubSubTopic.CHANNEL_POINTS_V1;
    }

    public static enum ChannelPointsRedemptionStatus {
        FULFILLED,
        UNFULFILLED
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class ChannelPointsReward {

        @JsonField("background_color")
        private String backgroundColor;

        @JsonField("channel_id")
        private String channelId;

        private String id;

        @JsonField("cooldown_expires_at")
        private Instant cooldownExpiresAt;

        private String title;

        private String prompt;

        private int cost;

        @JsonField("is_enabled")
        private boolean enabled;

        @JsonField("is_in_stock")
        private boolean inStock;

        @JsonField("is_paused")
        private boolean paused;

        @JsonField("is_sub_only")
        private boolean subOnly;

        @JsonField("is_user_input_required")
        private boolean userInputRequired;

        @Nullable
        private ChannelPointsImages image;

        @JsonField("default_image")
        private ChannelPointsImages defaultImage;

        @JsonField("max_per_stream")
        private ChannelPointsMaxPerStream maxPerStream;

        @JsonField("max_per_user_per_stream")
        private ChannelPointsMaxPerUserPerStream maxPerUserPerStream;

        @JsonField("global_cooldown")
        private ChannelPointsCooldown globalCooldown;

    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class ChannelPointsUser {
        @JsonField("login")
        private String username;

        private String id;

        @JsonField("display_name")
        private String displayname;

    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class ChannelPointsImages {

        @JsonField("url_1x")
        private String smallImage;

        @JsonField("url_2x")
        private String mediumImage;

        @JsonField("url_4x")
        private String largeImage;

    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class ChannelPointsMaxPerStream {

        @JsonField("is_enabled")
        private boolean enabled;

        @JsonField("max_per_stream")
        private int max;

    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class ChannelPointsMaxPerUserPerStream {

        @JsonField("is_enabled")
        private boolean enabled;

        @JsonField("max_per_user_per_stream")
        private int max;

    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class ChannelPointsCooldown {

        @JsonField("is_enabled")
        private boolean enabled;

        @JsonField("global_cooldown_seconds")
        private int globalCooldownSeconds;

    }

}
