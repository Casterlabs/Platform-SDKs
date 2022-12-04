package co.casterlabs.glimeshapijava.realtime;

import co.casterlabs.glimeshapijava.types.GlimeshChatMessage;

public interface GlimeshChatListener {

    default void onOpen() {}

    public void onChat(GlimeshChatMessage chat);

    default void onClose(boolean remote) {}

}
