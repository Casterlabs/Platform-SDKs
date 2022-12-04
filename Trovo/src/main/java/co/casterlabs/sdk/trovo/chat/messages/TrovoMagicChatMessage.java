package co.casterlabs.sdk.trovo.chat.messages;

import java.util.List;

import co.casterlabs.sdk.trovo.chat.TrovoMessageType;
import co.casterlabs.sdk.trovo.chat.TrovoRawChatMessage;
import co.casterlabs.sdk.trovo.chat.TrovoSubLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class TrovoMagicChatMessage implements TrovoMessage {
    private TrovoRawChatMessage raw;
    private @Getter TrovoMessageType type;

    public String getMessage() {
        return this.raw.content;
    }

    public String getSenderNickname() {
        return this.raw.nick_name;
    }

    public String getSenderAvatar() {
        return this.raw.avatar;
    }

    public TrovoSubLevel getSenderSubLevel() {
        return this.raw.sub_lv;
    }

    public List<String> getSenderMedals() {
        return this.raw.medals;
    }

    public List<String> getSenderRoles() {
        return this.raw.roles;
    }

    public String getMessageId() {
        return this.raw.message_id;
    }

    public String getSenderId() {
        return this.raw.sender_id;
    }

    @Override
    public boolean isCatchup() {
        return this.raw.is_catchup;
    }

}
