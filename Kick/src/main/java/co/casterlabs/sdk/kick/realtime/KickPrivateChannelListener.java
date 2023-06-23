package co.casterlabs.sdk.kick.realtime;

public interface KickPrivateChannelListener {

    default void onOpen() {}

    default void onClose() {}

    default void onFollow(String username, boolean isFollowing) {}

}
