package co.casterlabs.trovoapi.chat.messages;

import java.util.List;

import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.trovoapi.chat.TrovoMessageType;
import co.casterlabs.trovoapi.chat.TrovoRawChatMessage;
import co.casterlabs.trovoapi.chat.TrovoSpell;
import co.casterlabs.trovoapi.chat.TrovoSubLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TrovoSpellMessage implements TrovoMessage {
    private TrovoRawChatMessage raw;
    private JsonObject content;

    public TrovoSpell getSpell() {
        return TrovoSpell.get(this.getGift());
    }

    public String getGift() {
        return this.content.get("gift").getAsString();
    }

    public int getAmount() {
        return this.content.get("num").getAsNumber().intValue();
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
        return TrovoMessageType.SPELL;
    }

    @Override
    public boolean isCatchup() {
        return this.raw.is_catchup;
    }

}
