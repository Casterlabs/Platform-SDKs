package co.casterlabs.sdk.theta.realtime;

import co.casterlabs.sdk.theta.realtime.events.ThetaChatHello;
import co.casterlabs.sdk.theta.realtime.events.ThetaChatMessage;

public interface ThetaPubNubListener {

    public void onOpen();

    public void onClose();

    public void onChatMessage(ThetaChatMessage chatMessage);

    public void onChatHello(ThetaChatHello chatHello);

    public void onChannelOnline();

    public void onChannelOffline();

}
