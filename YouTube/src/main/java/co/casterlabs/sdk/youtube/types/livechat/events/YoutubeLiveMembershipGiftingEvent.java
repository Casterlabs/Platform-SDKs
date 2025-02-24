package co.casterlabs.sdk.youtube.types.livechat.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveMembershipGiftingEvent extends YoutubeLiveEvent {
    public final Integer giftMembershipsCount = null;
    public final String giftMembershipsLevelName = null;

}
