package co.casterlabs.sdk.youtube.types.livechat;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveChatAuthor {
    public final String channelId = null;
    public final String channelUrl = null;
    public final String displayName = null;
    public final String profileImageUrl = null;
    public final Boolean isVerified = null;
    public final Boolean isChatOwner = null;
    public final Boolean isChatSponsor = null;
    public final Boolean isChatModerator = null;

}
