package co.casterlabs.sdk.brime.realtime;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class BrimeChatMessage {
    @JsonField("xid")
    private String messageId;

    @JsonField("linked_xid")
    private String linkedMessageId;

    private String channel;

    private Instant timestamp;

    private BrimeChatMessage reply;

    @JsonField("user")
    private BrimeMessageSender sender;

    private BrimeMessageContent content;

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class BrimeMessageSender {
        private String xid;

        private String username;

        @JsonField("display_name")
        private String displayname;

        @JsonField("chat_color")
        private String color;

        @JsonField("is_staff")
        private boolean isStaff;

        @JsonField("is_broadcaster")
        private boolean isBroadcaster;

        @JsonField("is_vip")
        private boolean isVIP;

        @JsonField("is_mod")
        private boolean isModerator;

        public List<String> getBadges() {
            List<String> result = new ArrayList<>();

//            if (this.isStaff) {
//                result.add("https://assets.brimecdn.com/staff_badge.svg");
//            }

            if (this.isBroadcaster) {
                result.add("https://assets.brimecdn.com/broadcaster_badge.svg");
            }

            if (this.isVIP) {
                result.add("https://assets.brimecdn.com/vip_badge.svg");
            }

            if (this.isModerator) {
                result.add("https://assets.brimecdn.com/mod_badge.svg");
            }

            return result;
        }

    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class BrimeMessageContent {
        private String type;
        private String raw;
        private String parsed;

        private BrimeMessageMeta meta;

        @Getter
        @ToString
        @JsonClass(exposeAll = true)
        public static class BrimeMessageMeta {
            private BrimeMessageSender[] mentions;

            private BrimeMessageLink[] links;

            private BrimeMessageEmote[] emotes;

            @JsonField("attachements")
            private BrimeMessageAttachment[] attachments;

            @Getter
            @ToString
            @JsonClass(exposeAll = true)
            public static class BrimeMessageEmote {
                private String xid;
                private String code;
                private String src;
            }

            @Getter
            @ToString
            @JsonClass(exposeAll = true)
            public static class BrimeMessageLink {
                private String match;
                private String host;
            }

            @Getter
            @ToString
            @JsonClass(exposeAll = true)
            public static class BrimeMessageAttachment {
                private String type;
                private String mime;
                private String src;
                private String preview;
            }
        }
    }

}
