package co.casterlabs.sdk.kick.realtime;

public interface KickChannelListener {

    default void onOpen() {}

    default void onClose() {}

    default void onChannelLive(boolean isLive) {}

    default void onFollowersCountUpdate(int newFollowersCount) {}

}
