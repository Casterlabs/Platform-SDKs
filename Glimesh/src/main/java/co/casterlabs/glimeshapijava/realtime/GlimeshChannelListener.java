package co.casterlabs.glimeshapijava.realtime;

import co.casterlabs.glimeshapijava.types.GlimeshChannel;

public interface GlimeshChannelListener {

    default void onOpen() {}

    public void onUpdate(GlimeshChannel channel);

    default void onClose(boolean remote) {}

}
