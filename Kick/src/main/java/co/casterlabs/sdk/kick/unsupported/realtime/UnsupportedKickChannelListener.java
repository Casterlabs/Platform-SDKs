package co.casterlabs.sdk.kick.unsupported.realtime;

import co.casterlabs.sdk.kick.unsupported.realtime.types.UnsupportedKickRaidEvent;

public interface UnsupportedKickChannelListener {

    default void onOpen() {}

    default void onClose() {}

    default void onChannelLive(boolean isLive) {}

    default void onRaid(UnsupportedKickRaidEvent raidEvent) {}

    default void onRaidTarget(String slug) {}

}
