package co.casterlabs.sdk.trovo.chat.messages;

import java.util.List;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.trovo.chat.TrovoMessageType;
import co.casterlabs.sdk.trovo.chat.TrovoRawChatMessage;
import co.casterlabs.sdk.trovo.chat.TrovoSubLevel;
import lombok.SneakyThrows;

public class TrovoCustomSpellMessage extends TrovoMessage {
    private static final String IMAGE_FORMAT = "https://custom-file.trovo.live/file/%s/icon%d.png?imageView2/2/format/webp";

    private final JsonObject content;

    @SneakyThrows
    public TrovoCustomSpellMessage(TrovoRawChatMessage raw) {
        super(raw);
        this.content = Rson.DEFAULT.fromJson(raw.content, JsonObject.class);
    }

    public long getSpellId() {
        return this.content.get("sid").getAsNumber().longValue();
    }

    public String getImageLink(String channelId) {
        return String.format(IMAGE_FORMAT, channelId, this.getSpellId());
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
        return TrovoMessageType.CUSTOM_SPELL;
    }

}
