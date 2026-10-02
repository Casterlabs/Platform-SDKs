package co.casterlabs.sdk.x.v2.types;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class Xv2Broadcast {
    @JsonField("id")
    public final @Nullable String id = null;

    @JsonField("broadcast_id")
    public final @Nullable String broadcastId = null;

    @JsonField("media_key")
    public final @Nullable String mediaKey = null;

    /**
     * Known values: NOT_STARTED, RUNNING, TIMED_OUT, ENDED, CANCELED
     */
    @JsonField("state")
    public final @Nullable String state = null;

    /**
     * Broadcast title/status text.
     */
    @JsonField("status")
    public final @Nullable String status = null;

    @JsonField("twitter_user_id")
    public final @Nullable String twitterUserId = null;

    @JsonField("user_display_name")
    public final @Nullable String userDisplayName = null;

    @JsonField("twitter_username")
    public final @Nullable String twitterUsername = null;

    /**
     * Milliseconds since epoch, returned as a string.
     */
    @JsonField("created_at_ms")
    public final @Nullable String createdAtMs = null;

    /**
     * Milliseconds since epoch, returned as a string.
     */
    @JsonField("updated_at_ms")
    public final @Nullable String updatedAtMs = null;

    /**
     * Milliseconds since epoch, returned as a string.
     */
    @JsonField("start_ms")
    public final @Nullable String startMs = null;

    /**
     * Milliseconds since epoch, returned as a string.
     */
    @JsonField("end_ms")
    public final @Nullable String endMs = null;

    @JsonField("language")
    public final @Nullable String language = null;

    @JsonField("image_url")
    public final @Nullable String imageUrl = null;

    @JsonField("image_url_small")
    public final @Nullable String imageUrlSmall = null;

    @JsonField("tweet_id")
    public final @Nullable String tweetId = null;

    @JsonField("share_url")
    public final @Nullable String shareUrl = null;

    @JsonField("is_locked")
    public final @Nullable Boolean isLocked = null;

    /**
     * Inverse of the create-broadcast request's is_low_latency field.
     */
    @JsonField("is_high_latency")
    public final @Nullable Boolean isHighLatency = null;

    @JsonField("total_watching")
    public final @Nullable String totalWatching = null;

    @JsonField("total_watched")
    public final @Nullable String totalWatched = null;

    @JsonField("available_for_replay")
    public final @Nullable Boolean availableForReplay = null;

    /**
     * Known values: 0 = Default / legacy 1 = Chat disabled 2 = Everyone 3 =
     * Verified accounts 4 = Accounts followed by broadcaster 5 = Broadcaster's
     * subscribers
     */
    @JsonField("chat_option")
    public final @Nullable Integer chatOption = null;

    @JsonField("has_moderation")
    public final @Nullable Boolean hasModeration = null;

}
