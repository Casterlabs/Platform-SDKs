package co.casterlabs.sdk.trovo.chat;

import co.casterlabs.sdk.trovo.chat.messages.TrovoChatMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoCustomSpellMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoFollowMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoGiftSubMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoGiftSubRandomlyMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoMagicChatMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoPlatformEventMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoRaidWelcomeMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoSpellMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoSubscriptionMessage;
import co.casterlabs.sdk.trovo.chat.messages.TrovoWelcomeMessage;

public interface ChatListener {

    default void onOpen() {}

    default void onChatMessage(TrovoChatMessage message) {}

    default void onFollow(TrovoFollowMessage message) {}

    default void onGiftSub(TrovoGiftSubMessage message) {}

    default void onGiftSubRandomly(TrovoGiftSubRandomlyMessage message) {}

    default void onMagicChat(TrovoMagicChatMessage message) {}

    default void onPlatformEvent(TrovoPlatformEventMessage message) {}

    default void onRaidWelcome(TrovoRaidWelcomeMessage message) {}

    default void onSpell(TrovoSpellMessage message) {}

    default void onCustomSpell(TrovoCustomSpellMessage message) {}

    default void onSubscription(TrovoSubscriptionMessage message) {}

    default void onWelcome(TrovoWelcomeMessage message) {}

    default void onClose(boolean remote) {}

    public static void fireListener(TrovoMessage chat, ChatListener listener) {
        switch (chat.getType()) {
            case CHAT:
                listener.onChatMessage((TrovoChatMessage) chat);
                break;

            case FOLLOW:
                listener.onFollow((TrovoFollowMessage) chat);
                break;

            case GIFT_SUB_RANDOM:
                listener.onGiftSubRandomly((TrovoGiftSubRandomlyMessage) chat);
                break;

            case GIFT_SUB_USER:
                listener.onGiftSub((TrovoGiftSubMessage) chat);
                break;

            case MAGIC_CHAT_BULLET_SCREEN:
            case MAGIC_CHAT_COLORFUL:
            case MAGIC_CHAT_SPELL:
            case MAGIC_CHAT_SUPER_CAP:
                listener.onMagicChat((TrovoMagicChatMessage) chat);
                break;

            case SPELL:
                listener.onSpell((TrovoSpellMessage) chat);
                break;

            case PLATFORM_EVENT:
                listener.onPlatformEvent((TrovoPlatformEventMessage) chat);
                break;

            case RAID_WELCOME:
                listener.onRaidWelcome((TrovoRaidWelcomeMessage) chat);
                break;

            case SUBSCRIPTION:
                listener.onSubscription((TrovoSubscriptionMessage) chat);
                break;

            case WELCOME:
                listener.onWelcome((TrovoWelcomeMessage) chat);
                break;

            case CUSTOM_SPELL:
                listener.onCustomSpell((TrovoCustomSpellMessage) chat);
                break;

            case UNKNOWN:
                break;
        }
    }

}
