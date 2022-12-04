package co.casterlabs.dliveapijava.realtime;

import co.casterlabs.dliveapijava.realtime.events.DliveChatGift;
import co.casterlabs.dliveapijava.realtime.events.DliveChatHost;
import co.casterlabs.dliveapijava.realtime.events.DliveChatMessage;
import co.casterlabs.dliveapijava.realtime.events.DliveChatSender;
import co.casterlabs.dliveapijava.realtime.events.DliveChatSubscription;

public interface DliveChatListener {

    /* Lifecycle Methods */

    default void onOpen() {}

    default void onClose(boolean remote) {}

    /* Event Methods */

    public void onMessage(DliveChatMessage chatMessage);

    public void onGift(DliveChatGift chatGift);

    public void onLive();

    public void onOffline();

    public void onFollow(DliveChatSender follower);

    public void onSubscription(DliveChatSubscription subscription);

    public void onMessagesDelete(String[] ids);

    public void onHost(DliveChatHost host);

}
