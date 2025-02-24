package co.casterlabs.sdk.tiktok.types.unsupported;

import java.lang.reflect.Field;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class TiktokWebcastRoomData {
    public final Boolean showLiveChat = null;
    public final @JsonField("user") RoomUser accountUser = null;
    public final @JsonField("liveRoom") StreamInfo streamInfo = null;

    @ToString.Include
    public boolean isLive() {
        if (this.streamInfo == null) return false; // Never went live or properly ended?
        switch (this.streamInfo.status) {
            case 4: // Ended?
                return false;

            case 2:
            default:
//                System.out.println(this.streamInfo.status);
                return true;
        }
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class RoomUser {
        public final String id = null;
        public final String nickname = null;
        public final @JsonField("uniqueId") String handle = null;
        public final @JsonField("signature") String bio = null;
        public final @JsonField("avatarLarger") String avatarUrl = null;
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class StreamInfo {
        public final String coverUrl = null;
        public final String title = null;
        public final @JsonExclude Long startedAt = null;
        public final @JsonExclude Boolean isAgeRestricted = null;
        public final @JsonExclude Integer viewerCount = null;
        public final @JsonField("roomID") String chatRoomId = null;

        private final Integer status = null;

        @JsonDeserializationMethod("startTime")
        private void $deserialize_startTime(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
            long time = e.getAsNumber().longValue() * 1000; // Epoch seconds to milliseconds

            Field f = StreamInfo.class.getField("startedAt");
            f.setAccessible(true);
            f.set(this, time);
        }

        @JsonDeserializationMethod("streamId")
        private void $deserialize_streamId(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
            boolean is = e.getAsString().isEmpty(); // Empty = age restricted

            Field f = StreamInfo.class.getField("isAgeRestricted");
            f.setAccessible(true);
            f.set(this, is);
        }

        @JsonDeserializationMethod("liveRoomStats")
        private void $deserialize_liveRoomStats(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
            int count = e.getAsObject().getNumber("userCount").intValue();

            Field f = StreamInfo.class.getField("viewerCount");
            f.setAccessible(true);
            f.set(this, count);
        }

        // TODO streamData
        // TODO hevcStreamData
        // I want to map streamData to urls and names and resolutions and keys.

    }

}
