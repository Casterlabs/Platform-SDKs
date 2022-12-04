package co.casterlabs.sdk.glimesh.realtime;

import co.casterlabs.sdk.glimesh.types.GlimeshChatMessage;

public interface GlimeshChatListener {

    default void onOpen() {}

    public void onChat(GlimeshChatMessage chat);

    default void onClose(boolean remote) {}

}
