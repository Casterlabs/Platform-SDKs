package co.casterlabs.sdk.kick.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class KickChannel {

    public final String slug = null;

    @JsonField("channel_description")
    public final String channelDescription = null;

    @JsonField("banner_picture")
    public final String bannerPictureUrl = null;

    @JsonField("stream_title")
    public final String streamTitle = null;

    public final KickCategory category = null;

    public final KickStream stream = null;

}
