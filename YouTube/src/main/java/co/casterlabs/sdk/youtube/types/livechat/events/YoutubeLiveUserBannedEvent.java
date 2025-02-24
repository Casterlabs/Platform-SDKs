package co.casterlabs.sdk.youtube.types.livechat.events;

import java.lang.reflect.Field;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveUserBannedEvent extends YoutubeLiveEvent {
    public final Long banDurationSeconds = null;
    public final BannedUserDetails bannedUserDetails = null;

    public final @JsonExclude BanType banType = null;

    @JsonDeserializationMethod("banType")
    private void $deserialize_banType(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        BanType type = BanType.valueOf(e.getAsString().toUpperCase());

        Field f = YoutubeLiveUserBannedEvent.class.getField("banType");
        f.setAccessible(true);
        f.set(this, type);
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class BannedUserDetails {
        public final String channelId = null;
        public final String channelUrl = null;
        public final String displayName = null;
        public final String profileImageUrl = null;
    }

    public static enum BanType {
        PERMANENT,
        TEMPORARY;
    }

}
