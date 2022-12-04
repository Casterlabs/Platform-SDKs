package co.casterlabs.sdk.glimesh.realtime;

import co.casterlabs.sdk.glimesh.types.GlimeshChannel;

public interface GlimeshChannelListener {

    default void onOpen() {}

    public void onUpdate(GlimeshChannel channel);

    default void onClose(boolean remote) {}

}
