package co.casterlabs.youtubeapijava.types.livechat.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveMembershipGiftingEvent extends YoutubeLiveEvent {
    private int giftMembershipsCount;
    private String giftMembershipsLevelName;

}
