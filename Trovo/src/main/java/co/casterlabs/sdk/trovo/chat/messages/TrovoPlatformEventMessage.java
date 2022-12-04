package co.casterlabs.sdk.trovo.chat.messages;

import co.casterlabs.sdk.trovo.chat.TrovoMessageType;
import co.casterlabs.sdk.trovo.chat.TrovoRawChatMessage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TrovoPlatformEventMessage implements TrovoMessage {
    private TrovoRawChatMessage raw;

    public String getMessage() {
        return this.raw.content;
    }

    @Override
    public TrovoMessageType getType() {
        return TrovoMessageType.PLATFORM_EVENT;
    }

    @Override
    public boolean isCatchup() {
        return this.raw.is_catchup;
    }

}
