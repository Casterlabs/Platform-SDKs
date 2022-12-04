package co.casterlabs.sdk.trovo.requests.data;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@NonNull
@ToString
@JsonClass(exposeAll = true)
public class TrovoUser {
    private String username;

    private String nickname;

    @JsonField("user_id")
    private String userId;

    @JsonField("channel_id")
    private String channelId;

}
