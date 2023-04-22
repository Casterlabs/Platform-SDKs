package co.casterlabs.sdk.kick.realtime;

import co.casterlabs.sdk.kick.realtime.types.KickRaidEvent;

public interface KickChannelListener {

    default void onOpen() {}

    default void onClose() {}

    default void onChannelLive(boolean isLive) {}

    default void onFollowersCountUpdate(int newFollowersCount) {}

    default void onRaid(KickRaidEvent raidEvent) {}

    default void onRaidTarget(String slug) {}

}
