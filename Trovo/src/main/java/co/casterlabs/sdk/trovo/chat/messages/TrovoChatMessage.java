package co.casterlabs.sdk.trovo.chat.messages;

import java.util.List;

import co.casterlabs.sdk.trovo.chat.TrovoMessageType;
import co.casterlabs.sdk.trovo.chat.TrovoRawChatMessage;
import co.casterlabs.sdk.trovo.chat.TrovoSubLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TrovoChatMessage implements TrovoMessage {
    private TrovoRawChatMessage raw;

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
    public TrovoMessageType getType() {
        return TrovoMessageType.CHAT;
    }

    @Override
    public boolean isCatchup() {
        return this.raw.is_catchup;
    }

}
