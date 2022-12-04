package co.casterlabs.trovoapi.chat.messages;

import java.util.List;

import co.casterlabs.trovoapi.chat.TrovoMessageType;
import co.casterlabs.trovoapi.chat.TrovoRawChatMessage;
import co.casterlabs.trovoapi.chat.TrovoSubLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TrovoRaidWelcomeMessage implements TrovoMessage {
    private TrovoRawChatMessage raw;

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

    @Override
    public boolean isCatchup() {
        return this.raw.is_catchup;
    }

}
