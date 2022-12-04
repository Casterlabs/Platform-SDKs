package co.casterlabs.trovoapi.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TrovoMessageType {
    UNKNOWN(-1),
    CHAT(0),
    SPELL(5),
    MAGIC_CHAT_SUPER_CAP(6),
    MAGIC_CHAT_COLORFUL(7),
    MAGIC_CHAT_SPELL(8),
    MAGIC_CHAT_BULLET_SCREEN(9),
    SUBSCRIPTION(5001),
    FOLLOW(5003),
    WELCOME(5004),
    GIFT_SUB_RANDOM(5005),
    GIFT_SUB_USER(5006),
    PLATFORM_EVENT(5007),
    RAID_WELCOME(5008),
    CUSTOM_SPELL(5009),
    // STREAM_STATE(5012), // ?
    ;

    private int code;

    public static TrovoMessageType lookup(int code) {
        for (TrovoMessageType type : TrovoMessageType.values()) {
            if (type.code == code) {
                return type;
            }
        }

        return TrovoMessageType.UNKNOWN;
    }

}
