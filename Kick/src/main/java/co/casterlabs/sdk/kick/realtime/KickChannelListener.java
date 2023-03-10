package co.casterlabs.sdk.kick.realtime;

import co.casterlabs.sdk.kick.realtime.types.KickChatEvent;
import co.casterlabs.sdk.kick.realtime.types.KickReactionEvent;

public interface KickChannelListener {

    default void onOpen() {}

    default void onClose() {}

    default void onChannelLive(boolean isLive) {}

    default void onChat(KickChatEvent event) {}

    void onReaction(KickReactionEvent event);

}
