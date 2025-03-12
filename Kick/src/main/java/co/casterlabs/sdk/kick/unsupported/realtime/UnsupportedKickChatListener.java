package co.casterlabs.sdk.kick.unsupported.realtime;

import co.casterlabs.sdk.kick.unsupported.realtime.types.UnsupportedKickChatEvent;
import co.casterlabs.sdk.kick.unsupported.realtime.types.UnsupportedKickPollUpdateEvent;
import co.casterlabs.sdk.kick.unsupported.realtime.types.UnsupportedKickRaidEvent;

public interface UnsupportedKickChatListener {

    default void onOpen() {}

    default void onClose() {}

    default void onChat(UnsupportedKickChatEvent event) {}

    default void onDeleted(String messageId) {}

    default void onRaid(UnsupportedKickRaidEvent event) {}

    default void onClear() {}

    default void onSubscription(String username, int months) {}

    default void onGiftSubscriptions(String[] giftRecipients, String gifter) {}

    default void onPollUpdate(UnsupportedKickPollUpdateEvent event) {}

    default void onPollDelete() {}

    default void onUserBanned(String slug) {}
}
