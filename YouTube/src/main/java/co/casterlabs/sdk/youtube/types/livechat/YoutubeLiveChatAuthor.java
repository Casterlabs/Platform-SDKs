package co.casterlabs.sdk.youtube.types.livechat;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveChatAuthor {

    private String channelId;

    private String channelUrl;

    private String displayName;

    private String profileImageUrl;

    private boolean isVerified;

    private boolean isChatOwner;

    private boolean isChatSponsor;

    private boolean isChatModerator;

}
