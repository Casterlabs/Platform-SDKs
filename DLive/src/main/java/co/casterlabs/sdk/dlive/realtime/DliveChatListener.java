package co.casterlabs.sdk.dlive.realtime;

import co.casterlabs.sdk.dlive.realtime.events.DliveChatGift;
import co.casterlabs.sdk.dlive.realtime.events.DliveChatHost;
import co.casterlabs.sdk.dlive.realtime.events.DliveChatMessage;
import co.casterlabs.sdk.dlive.realtime.events.DliveChatSender;
import co.casterlabs.sdk.dlive.realtime.events.DliveChatSubscription;

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
