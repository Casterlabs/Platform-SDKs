package co.casterlabs.sdk.trovo.chat.messages;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.trovo.chat.TrovoMessageType;
import co.casterlabs.sdk.trovo.chat.TrovoRawChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public abstract class TrovoMessage {
    public final TrovoRawChatMessage raw;

    public abstract TrovoMessageType getType();

    public final boolean isCatchup() {
        return this.raw.is_catchup;
    }

    @SneakyThrows
    public static TrovoMessage parse(JsonObject chat, boolean isCatchup) {
        TrovoRawChatMessage raw = Rson.DEFAULT.fromJson(chat, TrovoRawChatMessage.class);
        TrovoMessageType type = TrovoMessageType.lookup(raw.type);

        raw.is_catchup = isCatchup;

        if (raw.avatar != null && !raw.avatar.contains("://")) {
            raw.avatar = "https://headicon.trovo.live/user/" + raw.avatar; // Damn you Trovo.
        }

        switch (type) {
            case CHAT:
                return new TrovoChatMessage(raw);

            case FOLLOW:
                return new TrovoFollowMessage(raw);

            case GIFT_SUB_RANDOM:
                return new TrovoGiftSubRandomlyMessage(raw);

            case GIFT_SUB_USER:
                return new TrovoGiftSubMessage(raw);

            case MAGIC_CHAT_BULLET_SCREEN:
            case MAGIC_CHAT_COLORFUL:
            case MAGIC_CHAT_SPELL:
            case MAGIC_CHAT_SUPER_CAP:
                return new TrovoMagicChatMessage(raw, type);

            case PLATFORM_EVENT:
                return new TrovoPlatformEventMessage(raw);

            case RAID_WELCOME:
                return new TrovoRaidWelcomeMessage(raw);

            case SPELL:
                return new TrovoSpellMessage(raw);

            case SUBSCRIPTION:
                return new TrovoSubscriptionMessage(raw);

            case WELCOME:
                return new TrovoWelcomeMessage(raw);

            case CUSTOM_SPELL:
                return new TrovoCustomSpellMessage(raw);

            case UNKNOWN:
                return null;
        }
        return null;
    }

}
