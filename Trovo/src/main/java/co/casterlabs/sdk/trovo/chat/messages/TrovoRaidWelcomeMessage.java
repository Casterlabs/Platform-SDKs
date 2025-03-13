package co.casterlabs.sdk.trovo.chat.messages;

import java.util.List;

import co.casterlabs.sdk.trovo.chat.TrovoMessageType;
import co.casterlabs.sdk.trovo.chat.TrovoRawChatMessage;
import co.casterlabs.sdk.trovo.chat.TrovoSubLevel;

public class TrovoRaidWelcomeMessage extends TrovoMessage {

    public TrovoRaidWelcomeMessage(TrovoRawChatMessage raw) {
        super(raw);
    }

    public String getMessage() {
        return this.raw.content;
    }

    public String getRaiderNickname() {
        return this.raw.nick_name;
    }

    public String getRaidAvatar() {
        return this.raw.avatar;
    }

    public TrovoSubLevel getRaiderSubLevel() {
        return this.raw.sub_lv;
    }

    public List<String> getRaiderMedals() {
        return this.raw.medals;
    }

    public List<String> getRaidRoles() {
        return this.raw.roles;
    }

    public String getRaiderId() {
        return this.raw.sender_id;
    }

    @Override
    public TrovoMessageType getType() {
        return TrovoMessageType.RAID_WELCOME;
    }

}
