package co.casterlabs.sdk.trovo.chat.messages;

import co.casterlabs.sdk.trovo.chat.TrovoMessageType;
import co.casterlabs.sdk.trovo.chat.TrovoRawChatMessage;

public class TrovoPlatformEventMessage extends TrovoMessage {

    public TrovoPlatformEventMessage(TrovoRawChatMessage raw) {
        super(raw);
    }

    public String getMessage() {
        return this.raw.content;
    }

    @Override
    public TrovoMessageType getType() {
        return TrovoMessageType.PLATFORM_EVENT;
    }

}
