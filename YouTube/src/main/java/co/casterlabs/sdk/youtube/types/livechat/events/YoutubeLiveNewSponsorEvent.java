package co.casterlabs.sdk.youtube.types.livechat.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveNewSponsorEvent extends YoutubeLiveEvent {
    private String memberLevelName;
    private boolean isUpgrade;

}
