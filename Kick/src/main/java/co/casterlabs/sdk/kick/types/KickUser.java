package co.casterlabs.sdk.kick.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class KickUser {

    private long id;

    private String username;

    private String bio;

    @JsonField("profile_pic")
    private String profilePicture;

    @Getter(AccessLevel.NONE)
    @JsonField("streamer_channel")
    private JsonObject streamerChannel;

    public String getChannelSlug() {
        if (this.streamerChannel == null || !this.streamerChannel.isJsonObject()) return this.username;

        return this.streamerChannel.getString("slug");
    }

}
