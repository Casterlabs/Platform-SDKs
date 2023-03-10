package co.casterlabs.sdk.kick.realtime.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class KickChatEvent {
    private KickChatMessage message;
    private KickChatUser user;

}
