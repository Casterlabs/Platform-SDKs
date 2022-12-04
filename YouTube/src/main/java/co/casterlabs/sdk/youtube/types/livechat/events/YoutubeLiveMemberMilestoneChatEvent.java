package co.casterlabs.sdk.youtube.types.livechat.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveMemberMilestoneChatEvent extends YoutubeLiveEvent {
    private String userComment;
    private int memberMonth;
    private String memberLevelName;

}
