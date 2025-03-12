package co.casterlabs.sdk.kick.unsupported.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class UnsupportedKickEmoji {

    private String name;
    private String image;

    public String getAsChatEmote() {
        return String.format("[emoji:%s]", this.name);
    }

}
