package co.casterlabs.sdk.kick.types;

import java.util.List;

import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class KickPostedChatMessage {
    public static final TypeToken<KickPostedChatMessage[]> ARRAY_TYPE = TypeToken.of(KickPostedChatMessage[].class);
    public static final TypeToken<List<KickPostedChatMessage>> LIST_TYPE = new TypeToken<List<KickPostedChatMessage>>() {
    };

    @JsonField("is_sent")
    public final Boolean sent = null;

    @JsonField("message_id")
    public final String messageId = null;

}
