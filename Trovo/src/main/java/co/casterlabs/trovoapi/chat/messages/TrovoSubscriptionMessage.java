package co.casterlabs.trovoapi.chat.messages;

import java.util.List;

import co.casterlabs.trovoapi.chat.TrovoMessageType;
import co.casterlabs.trovoapi.chat.TrovoRawChatMessage;
import co.casterlabs.trovoapi.chat.TrovoSubLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TrovoSubscriptionMessage implements TrovoMessage {
    private TrovoRawChatMessage raw;

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

    @Override
    public boolean isCatchup() {
        return this.raw.is_catchup;
    }

}
