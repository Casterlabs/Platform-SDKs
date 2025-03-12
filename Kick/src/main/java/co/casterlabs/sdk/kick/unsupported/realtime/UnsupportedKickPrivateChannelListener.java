package co.casterlabs.sdk.kick.unsupported.realtime;

public interface UnsupportedKickPrivateChannelListener {

    default void onOpen() {}

    default void onClose() {}

    default void onFollow(String username, boolean isFollowing) {}

    default void onSubscription(String username, int months) {}

    default void onGiftSubscriptions(String gifter, int recipientCount) {}

}
