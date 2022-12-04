package co.casterlabs.thetaapijava.realtime;

import co.casterlabs.thetaapijava.realtime.events.ThetaChatHello;
import co.casterlabs.thetaapijava.realtime.events.ThetaChatMessage;

public interface ThetaPubNubListener {

    public void onOpen();

    public void onClose();

    public void onChatMessage(ThetaChatMessage chatMessage);

    public void onChatHello(ThetaChatHello chatHello);

    public void onChannelOnline();

    public void onChannelOffline();

}
