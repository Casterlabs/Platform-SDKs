package co.casterlabs.sdk.trovo.chat.messages;

import java.util.List;

import co.casterlabs.sdk.trovo.chat.TrovoMessageType;
import co.casterlabs.sdk.trovo.chat.TrovoRawChatMessage;
import co.casterlabs.sdk.trovo.chat.TrovoSubLevel;

public class TrovoGiftSubRandomlyMessage extends TrovoMessage {

    public TrovoGiftSubRandomlyMessage(TrovoRawChatMessage raw) {
        super(raw);
    }

    public int getGiftAmount() {
        return Integer.parseInt(this.raw.content);
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

    public String getSenderId() {
        return this.raw.sender_id;
    }

    @Override
    public TrovoMessageType getType() {
        return TrovoMessageType.GIFT_SUB_RANDOM;
    }

}
