package co.casterlabs.sdk.trovo.chat.messages;

import co.casterlabs.sdk.trovo.chat.TrovoMessageType;

public interface TrovoMessage {

    public TrovoMessageType getType();

    public boolean isCatchup();

}
