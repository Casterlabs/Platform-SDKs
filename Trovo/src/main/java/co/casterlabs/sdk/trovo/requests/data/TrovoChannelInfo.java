package co.casterlabs.sdk.trovo.requests.data;

import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class TrovoChannelInfo {
    @JsonField("uid")
    public final String id = null;

    @JsonField("is_live")
    public final Boolean isLive = null;

    @JsonField("category_id")
    public final String categoryId = null;

    @JsonField("category_name")
    public final String categoryName = null;

    @JsonField("live_title")
    public final String streamTitle = null;

    @JsonField("audi_type")
    public final TrovoAudienceType audienceType = null;

    @JsonField("language_code")
    public final String languageCode = null;

    @JsonField("thumbnail")
    public final String thumbnailUrl = null;

    @JsonField("current_viewers")
    public final Long currentViewers = null;

    @JsonField("followers")
    public final Long followersCount = null;

    @JsonField("streamer_info")
    public final String streamerInfo = null;

    @JsonField("profile_pic")
    public final String profilePictureUrl = null;

    @JsonField("channel_url")
    public final String channelUrl = null;

    @JsonField("created_at")
    public final String createdAt = null;

    @JsonField("subscriber_num")
    public final Long subscribers = null;

    public final String username = null;

    @JsonField("social_links")
    public final List<Social> socials = null;

    /**
     * Requires channel_details_self and to be requested as auth.
     */
    @JsonField("stream_key")
    public final String streamKey = null;

    @ToString
    @JsonClass(exposeAll = true)
    public static class Social {
        public final String type = null;
        public final String url = null;

    }

}
