package co.casterlabs.sdk.kick.realtime;

import co.casterlabs.sdk.kick.realtime.types.KickChatEvent;
import co.casterlabs.sdk.kick.realtime.types.KickReactionEvent;

public interface KickChatListener {

    default void onOpen() {}

    default void onClose() {}

    default void onChat(KickChatEvent event) {}

    default void onReaction(KickReactionEvent event) {}

}
