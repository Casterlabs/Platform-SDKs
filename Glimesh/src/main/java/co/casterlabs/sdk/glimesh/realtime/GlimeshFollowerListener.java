package co.casterlabs.sdk.glimesh.realtime;

import co.casterlabs.sdk.glimesh.types.GlimeshUser;

public interface GlimeshFollowerListener {

    default void onOpen() {}

    public void onFollow(GlimeshUser follower);

    default void onClose(boolean remote) {}

}
