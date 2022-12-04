package co.casterlabs.trovoapi.chat.messages;

import co.casterlabs.trovoapi.chat.TrovoMessageType;

public interface TrovoMessage {

    public TrovoMessageType getType();

    public boolean isCatchup();

}
