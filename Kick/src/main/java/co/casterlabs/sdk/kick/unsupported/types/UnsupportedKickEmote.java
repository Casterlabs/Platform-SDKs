package co.casterlabs.sdk.kick.unsupported.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class UnsupportedKickEmote {

    private long id;

    private String name;

    @JsonField("subscribers_only")
    private boolean subscribersOnly;

    public String getImage() {
        return String.format("https://files.kick.com/emotes/%d/fullsize", this.id);
    }

    public String getAsChatEmote() {
        return String.format("[emote:%s:%s]", this.id, this.name);
    }

}
