package co.casterlabs.sdk.trovo.chat.messages;

import java.util.List;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.trovo.chat.TrovoMessageType;
import co.casterlabs.sdk.trovo.chat.TrovoRawChatMessage;
import co.casterlabs.sdk.trovo.chat.TrovoSubLevel;
import lombok.SneakyThrows;

public class TrovoSpellMessage extends TrovoMessage {
    private final JsonObject content;

    @SneakyThrows
    public TrovoSpellMessage(TrovoRawChatMessage raw) {
        super(raw);
        this.content = Rson.DEFAULT.fromJson(raw.content, JsonObject.class);
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

}
