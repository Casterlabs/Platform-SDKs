package co.casterlabs.sdk.brime.realtime;

public interface BrimeRealtimeListener {

    default void onOpen() {}

    public void onChat(BrimeChatMessage chat);

    public void onChatClear();

    public void onChatMessageDelete(String xid);

    public void onChannelWentLive();

    public void onChannelWentOffline();

    public void onViewerJoin(String xid);

    public void onViewerLeave(String xid);

    default void onClose(boolean remote) {}

    default void onDelete(String targetMessageXid) {}

}
