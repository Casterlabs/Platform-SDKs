package co.casterlabs.trovoapi.chat;

import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;

@JsonClass(exposeAll = true)
public class TrovoRawChatMessage {
    public int type;
    public String content;
    public String nick_name;
    public @JsonExclude String sender_id;
    public String message_id;
    public String avatar;
    public TrovoSubLevel sub_lv;
    public List<String> medals;
    public List<String> roles;
    public JsonObject content_data;
    public boolean is_catchup;

    // FUCK YOU TROVO.
    @JsonDeserializationMethod("sender_id")
    private void $deserialize_sender_id(JsonElement e) {
        if (e.isJsonString()) {
            this.sender_id = e.getAsString();
        } else {
            this.sender_id = e.toString();
        }
    }

}
