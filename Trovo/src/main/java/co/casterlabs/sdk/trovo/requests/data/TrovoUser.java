package co.casterlabs.sdk.trovo.requests.data;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class TrovoUser {
    public final String username = null;

    public final String nickname = null;

    @JsonField("user_id")
    public final String userId = null;

    @JsonField("channel_id")
    public final String channelId = null;

}
