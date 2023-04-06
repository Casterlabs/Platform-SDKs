package co.casterlabs.sdk.kick.types;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.sdk.kick.KickApi;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class KickChannel {

    private long id;

    private KickUser user;

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
        this.bannerImage = KickApi.parseResponsiveImage(e);
    }

    @JsonField("livestream")
    private @Nullable KickLiveStream liveStreamDetails; // Null if offline.

    @JsonField("can_host")
    private boolean canHost;

    @JsonExclude
    private boolean isVerified;

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

}
