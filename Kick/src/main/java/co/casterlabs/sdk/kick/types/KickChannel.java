package co.casterlabs.sdk.kick.types;

import java.util.List;

import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class KickChannel {
    public static final TypeToken<KickChannel[]> ARRAY_TYPE = TypeToken.of(KickChannel[].class);
    public static final TypeToken<List<KickChannel>> LIST_TYPE = new TypeToken<List<KickChannel>>() {
    };

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
