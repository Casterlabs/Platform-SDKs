package co.casterlabs.sdk.kick.unsupported.types;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickApi;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class UnsupportedKickChannel {

    private long id;

    private UnsupportedKickUser user;

    private String slug;

    @JsonField("playback_url")
    private String playbackUrl; // hls playlist url, 404s if offline.

    @JsonField("subscription_enabled")
    private boolean subscriptionsEnabled;

    @JsonField("vod_enabled")
    private boolean vodsEnabled;

//    @JsonField("followers_count") // misspelled.
    private int followersCount;

    @JsonExclude
    private @Nullable String bannerImage;

    @JsonDeserializationMethod("banner_image")
    private void $deserialize_bannerImage(JsonElement e) {
        this.bannerImage = UnsupportedKickApi.parseResponsiveImage(e);
    }

    @JsonField("livestream")
    private @Nullable UnsupportedKickLiveStream liveStreamDetails; // Null if offline.

    @JsonField("can_host")
    private boolean canHost;

    @JsonExclude
    private boolean isVerified;

    @JsonField("subscriber_badges")
    private SubscriberBadge[] subscriberBadges = {};

    @JsonDeserializationMethod("verified")
    private void $deserialize_verified(JsonElement e) {
        this.isVerified = !e.isJsonNull(); // Null means not verified.
    }

    @JsonExclude
    private long chatRoomId;

    @JsonDeserializationMethod("chatroom")
    private void $deserialize_chatroomId(JsonElement e) {
        this.chatRoomId = e.getAsObject().getNumber("id").longValue();
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class SubscriberBadge {
        private int id;
        private int months;

        public String getImageURL() {
            return String.format("https://files.kick.com/channel_subscriber_badges/%d/original", this.id);
        }

    }

}
