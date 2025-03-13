package co.casterlabs.sdk.trovo.chat.messages;

import java.util.List;

import co.casterlabs.sdk.trovo.chat.TrovoMessageType;
import co.casterlabs.sdk.trovo.chat.TrovoRawChatMessage;
import co.casterlabs.sdk.trovo.chat.TrovoSubLevel;

public class TrovoWelcomeMessage extends TrovoMessage {

    public TrovoWelcomeMessage(TrovoRawChatMessage raw) {
        super(raw);
    }

    public String getViewerNickname() {
        return this.raw.nick_name;
    }

    public String getViewerAvatar() {
        return this.raw.avatar;
    }

    public TrovoSubLevel getViewerSubLevel() {
        return this.raw.sub_lv;
    }

    public List<String> getViewerMedals() {
        return this.raw.medals;
    }

    public List<String> getViewerRoles() {
        return this.raw.roles;
    }

    public String getViewerId() {
        return this.raw.sender_id;
    }

    @Override
    public TrovoMessageType getType() {
        return TrovoMessageType.WELCOME;
    }

}
