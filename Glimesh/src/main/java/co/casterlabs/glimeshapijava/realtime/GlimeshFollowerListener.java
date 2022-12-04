package co.casterlabs.glimeshapijava.realtime;

import co.casterlabs.glimeshapijava.types.GlimeshUser;

public interface GlimeshFollowerListener {

    default void onOpen() {}

    public void onFollow(GlimeshUser follower);

    default void onClose(boolean remote) {}

}
