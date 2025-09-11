package co.casterlabs.sdk.tiktok.unsupported.realtime;

public interface TiktokWebcastImRoomListener {

    default void onOpen() {}

    default void onEvent(Object event) {}

    default void onClose(boolean remote) {}

}
