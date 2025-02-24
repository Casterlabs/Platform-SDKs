package co.casterlabs.sdk.youtube.types.livechat.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveMemberMilestoneChatEvent extends YoutubeLiveEvent {
    public final String userComment = null;
    public final Integer memberMonth = null;
    public final String memberLevelName = null;

}
