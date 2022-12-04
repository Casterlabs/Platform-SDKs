package co.casterlabs.youtubeapijava.types.livechat.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveUserBannedEvent extends YoutubeLiveEvent {
    private @JsonExclude BanType banType;
    private long banDurationSeconds;
    private BannedUserDetails bannedUserDetails;

    @JsonDeserializationMethod("banType")
    private void $deserialize_banType(JsonElement e) {
        this.banType = BanType.valueOf(e.getAsString().toUpperCase());
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class BannedUserDetails {
        private String channelId;
        private String channelUrl;
        private String displayName;
        private String profileImageUrl;
    }

    public static enum BanType {
        PERMANENT,
        TEMPORARY;
    }

}
