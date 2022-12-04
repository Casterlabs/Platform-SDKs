package co.casterlabs.sdk.trovo.requests.data;

import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@NonNull
@ToString
@JsonClass(exposeAll = true)
public class TrovoChannelInfo {
    @JsonField("uid")
    private String id;

    @JsonField("is_live")
    private boolean isLive;

    @JsonField("category_id")
    private String categoryId;

    @JsonField("category_name")
    private String categoryName;

    @JsonField("live_title")
    private String streamTitle;

    @JsonField("audi_type")
    private TrovoAudienceType audienceType;

    @JsonField("language_code")
    private String languageCode;

    @JsonField("thumbnail")
    private String thumbnailLink;

    @JsonField("current_viewers")
    private long currentViewers;

    @JsonField("followers")
    private long followers;

    @JsonField("streamer_info")
    private String streamerInfo;

    @JsonField("profile_pic")
    private String profilePictureLink;

    @JsonField("channel_url")
    private String channelUrl;

    @JsonField("created_at")
    private String createdAt;

    @JsonField("subscriber_num")
    private long subscribers;

    @JsonField("stream_key")
    private String streamKey;

    private String username;

    @JsonField("social_links")
    private List<Social> socials;

    @Getter
    @NonNull
    @ToString
    @JsonClass(exposeAll = true)
    public static class Social {
        private String type;

        @JsonField("url")
        private String link;

    }

}
