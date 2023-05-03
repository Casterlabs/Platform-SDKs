package co.casterlabs.sdk.kick.realtime;

import co.casterlabs.sdk.kick.realtime.types.KickChatEvent;
import co.casterlabs.sdk.kick.realtime.types.KickRaidEvent;

public interface KickChatListener {

    default void onOpen() {}

    default void onClose() {}

    default void onChat(KickChatEvent event) {}

    default void onDeleted(String messageId) {}

    default void onRaid(KickRaidEvent event) {}

    default void onClear() {}

    default void onSubscription(String username, int months) {}

    default void onGiftSubscriptions(String[] giftRecipients, String gifter) {}

}
