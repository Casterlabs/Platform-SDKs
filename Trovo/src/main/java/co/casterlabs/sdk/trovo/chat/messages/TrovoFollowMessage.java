package co.casterlabs.sdk.trovo.chat.messages;

import java.util.List;

import co.casterlabs.sdk.trovo.chat.TrovoMessageType;
import co.casterlabs.sdk.trovo.chat.TrovoRawChatMessage;
import co.casterlabs.sdk.trovo.chat.TrovoSubLevel;

public class TrovoFollowMessage extends TrovoMessage {

    public TrovoFollowMessage(TrovoRawChatMessage raw) {
        super(raw);
    }

    public String getFollowerNickname() {
        return this.raw.nick_name;
    }

    public String getFollowerAvatar() {
        return this.raw.avatar;
    }

    public TrovoSubLevel getFollowerSubLevel() {
        return this.raw.sub_lv;
    }

    public List<String> getFollowerMedals() {
        return this.raw.medals;
    }

    public List<String> getFollowerRoles() {
        return this.raw.roles;
    }

    public String getFollowerId() {
        return this.raw.sender_id;
    }

    @Override
    public TrovoMessageType getType() {
        return TrovoMessageType.FOLLOW;
    }

}
