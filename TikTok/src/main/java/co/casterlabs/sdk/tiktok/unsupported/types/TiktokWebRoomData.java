package co.casterlabs.sdk.tiktok.unsupported.types;

import java.lang.reflect.Field;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.ToString;

@ToString(onlyExplicitlyIncluded = true)
@JsonClass(exposeAll = true)
public class TiktokWebRoomData implements TiktokRoomData {
    public final WebRoomUserStats stats = null;
    public final @JsonField("user") WebRoomUser accountUser = null;
    public final @JsonField("liveRoom") WebRoomStreamInfo streamInfo = null;

    @Override
    @ToString.Include
    public String chatRoomId() {
        if (this.accountUser.chatRoomId != null && !this.accountUser.chatRoomId.isEmpty()) return this.accountUser.chatRoomId;
        if (this.streamInfo != null && this.streamInfo.chatRoomId != null && !this.streamInfo.chatRoomId.isEmpty()) return this.streamInfo.chatRoomId;
        return null;
    }

    @Override
    @ToString.Include
    public boolean isLive() {
        if (this.streamInfo == null) return false; // Never went live or was properly ended?
        switch (this.streamInfo.status) {
            case 4: // Ended?
                return false;

            case 2:
            default:
//                System.out.println(this.streamInfo.status);
                return true;
        }
    }

    @Override
    @ToString.Include
    public boolean isAgeRestricted() {
        if (this.streamInfo == null) return false; // Never went live or was properly ended?
        return this.streamInfo.isAgeRestricted;
    }

    @Override
    @ToString.Include
    public @Nullable String title() {
        if (this.streamInfo == null) return null; // Never went live or was properly ended?
        return this.streamInfo.title;
    }

    @Override
    @ToString.Include
    public @Nullable String coverUrl() {
        if (this.streamInfo == null) return null; // Never went live or was properly ended?
        return this.streamInfo.coverUrl;
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class WebRoomUserStats {
        public final Integer followingCount = null;
        public final Integer followerCount = null;
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class WebRoomUser {
        public final String id = null;
        public final String nickname = null;
        public final @JsonField("uniqueId") String handle = null;
        public final @JsonField("signature") String bio = null;
        public final @JsonField("avatarLarger") String avatarUrl = null;
        public final @JsonField("roomId") String chatRoomId = null;

    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class WebRoomStreamInfo {
        public final String coverUrl = null;
        public final String title = null;
        public final @JsonExclude Long startedAt = null;
        public final @JsonExclude Boolean isAgeRestricted = null;
        public final @JsonExclude Integer viewerCount = null;
        public final @JsonField("roomID") String chatRoomId = null;

        public final Integer status = null;

        @JsonDeserializationMethod("startTime")
        private void $deserialize_startTime(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
            long time = e.getAsNumber().longValue() * 1000; // Epoch seconds to milliseconds

            Field f = WebRoomStreamInfo.class.getField("startedAt");
            f.setAccessible(true);
            f.set(this, time);
        }

        @JsonDeserializationMethod("streamId")
        private void $deserialize_streamId(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
            boolean is = e.getAsString().isEmpty(); // Empty = age restricted

            Field f = WebRoomStreamInfo.class.getField("isAgeRestricted");
            f.setAccessible(true);
            f.set(this, is);
        }

        @JsonDeserializationMethod("liveRoomStats")
        private void $deserialize_liveRoomStats(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
            int count = e.getAsObject().getNumber("userCount").intValue();

            Field f = WebRoomStreamInfo.class.getField("viewerCount");
            f.setAccessible(true);
            f.set(this, count);
        }

        // TODO streamData
        // TODO hevcStreamData
        // I want to map streamData to urls and names and resolutions and keys.

    }

}
