package co.casterlabs.trovoapi.chat;

import java.util.List;

import co.casterlabs.trovoapi.chat.messages.TrovoChatMessage;
import co.casterlabs.trovoapi.chat.messages.TrovoCustomSpellMessage;
import co.casterlabs.trovoapi.chat.messages.TrovoFollowMessage;
import co.casterlabs.trovoapi.chat.messages.TrovoGiftSubMessage;
import co.casterlabs.trovoapi.chat.messages.TrovoGiftSubRandomlyMessage;
import co.casterlabs.trovoapi.chat.messages.TrovoMagicChatMessage;
import co.casterlabs.trovoapi.chat.messages.TrovoMessage;
import co.casterlabs.trovoapi.chat.messages.TrovoPlatformEventMessage;
import co.casterlabs.trovoapi.chat.messages.TrovoRaidWelcomeMessage;
import co.casterlabs.trovoapi.chat.messages.TrovoSpellMessage;
import co.casterlabs.trovoapi.chat.messages.TrovoSubscriptionMessage;
import co.casterlabs.trovoapi.chat.messages.TrovoWelcomeMessage;

public interface ChatListener {

    // Usually a bunch of messages means it's the chat history.
    default void onBatchMessages(List<TrovoMessage> messages) {
        for (TrovoMessage message : messages) {
            switch (message.getType()) {
                case CHAT:
                    this.onChatMessage((TrovoChatMessage) message);
                    break;

                case FOLLOW:
                    this.onFollow((TrovoFollowMessage) message);
                    break;

                case GIFT_SUB_RANDOM:
                    this.onGiftSubRandomly((TrovoGiftSubRandomlyMessage) message);
                    break;

                case GIFT_SUB_USER:
                    this.onGiftSub((TrovoGiftSubMessage) message);
                    break;

                case MAGIC_CHAT_BULLET_SCREEN:
                case MAGIC_CHAT_COLORFUL:
                case MAGIC_CHAT_SPELL:
                case MAGIC_CHAT_SUPER_CAP:
                    this.onMagicChat((TrovoMagicChatMessage) message);
                    break;

                case SPELL:
                    this.onSpell((TrovoSpellMessage) message);
                    break;

                case PLATFORM_EVENT:
                    this.onPlatformEvent((TrovoPlatformEventMessage) message);
                    break;

                case RAID_WELCOME:
                    this.onRaidWelcome((TrovoRaidWelcomeMessage) message);
                    break;

                case SUBSCRIPTION:
                    this.onSubscription((TrovoSubscriptionMessage) message);
                    break;

                case WELCOME:
                    this.onWelcome((TrovoWelcomeMessage) message);
                    break;

                case CUSTOM_SPELL:
                    this.onCustomSpell((TrovoCustomSpellMessage) message);
                    break;

                case UNKNOWN:
                    break;
            }
        }
    }

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

}
