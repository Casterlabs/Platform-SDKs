package co.casterlabs.sdk.tiktok.types.unsupported;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class TiktokWebcastRoomData {
    private boolean showLiveChat;
    private @JsonField("user") RoomUser accountUser;
    private @JsonField("liveRoom") StreamInfo streamInfo;

    @SuppressWarnings("unused")
    private boolean isLive; // To trick lombok

    public boolean getIsLive() {
        if (this.streamInfo == null) return false; // Never went live or properly ended?
        switch (this.streamInfo.status) {
            case 4: // Ended?
                return false;

            case 2:
            default:
                System.out.println(this.streamInfo.status);
                return true;
        }
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class RoomUser {
        private String id;
        private String nickname;
        private @JsonField("uniqueId") String handle;
        private @JsonField("signature") String bio;
        private @JsonField("avatarLarger") String avatarUrl;
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class StreamInfo {
        private String coverUrl;
        private String title;
        private @JsonExclude long startedAt;
        private @JsonExclude boolean isAgeRestricted;
        private @JsonExclude int viewerCount;
        private @JsonField("roomID") String chatRoomId;
        private @Getter(AccessLevel.NONE) int status;

        @JsonDeserializationMethod("startTime")
        private void $deserialize_startTime(JsonElement e) {
            this.startedAt = e.getAsNumber().longValue() * 1000; // Epoch seconds to milliseconds
        }

        @JsonDeserializationMethod("streamId")
        private void $deserialize_streamId(JsonElement e) {
            this.isAgeRestricted = e.getAsString().isEmpty();
        }

        @JsonDeserializationMethod("liveRoomStats")
        private void $deserialize_liveRoomStats(JsonElement e) {
            this.viewerCount = e.getAsObject().getNumber("userCount").intValue();
        }

        // TODO streamData
        // TODO hevcStreamData
        // I want to map streamData to urls and names and resolutions and keys.

    }

}
