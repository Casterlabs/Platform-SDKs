package co.casterlabs.sdk.youtube.types.livechat.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveNewSponsorEvent extends YoutubeLiveEvent {
    public final String memberLevelName = null;
    public final Boolean isUpgrade = null;

}
