package co.casterlabs.sdk.trovo.chat.messages;

import java.util.List;

import co.casterlabs.sdk.trovo.chat.TrovoMessageType;
import co.casterlabs.sdk.trovo.chat.TrovoRawChatMessage;
import co.casterlabs.sdk.trovo.chat.TrovoSubLevel;

public class TrovoSubscriptionMessage extends TrovoMessage {

    public TrovoSubscriptionMessage(TrovoRawChatMessage raw) {
        super(raw);
    }

    public String getSubscriberNickname() {
        return this.raw.nick_name;
    }

    public String getSubscriberAvatar() {
        return this.raw.avatar;
    }

    public TrovoSubLevel getSubscriberSubLevel() {
        return this.raw.sub_lv;
    }

    public List<String> getSubscriberMedals() {
        return this.raw.medals;
    }

    public List<String> getSubscriberRoles() {
        return this.raw.roles;
    }

    public String getSubscriberId() {
        return this.raw.sender_id;
    }

    @Override
    public TrovoMessageType getType() {
        return TrovoMessageType.SUBSCRIPTION;
    }

}
