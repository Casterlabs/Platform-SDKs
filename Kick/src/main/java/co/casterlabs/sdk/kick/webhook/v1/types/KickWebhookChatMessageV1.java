package co.casterlabs.sdk.kick.webhook.v1.types;

import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.sdk.kick.webhook.KickWebhookEvent;

@JsonClass(exposeAll = true)
public class KickWebhookChatMessageV1 implements KickWebhookEvent {

    public final KickWebhookUserV1 broadcaster = null;

    public final KickWebhookUserV1 sender = null;

    public final String content = null;

    @JsonField("message_id")
    public final String id = null;

    public final List<Emote> emotes = null;

    @JsonClass(exposeAll = true)
    public static class Emote {

        @JsonField("emote_id")
        public final String id = null;

        public final List<Position> positions = null;

        @JsonClass(exposeAll = true)
        public class Position {

            @JsonField("s")
            public final Integer start = null;

            @JsonField("e")
            public final Integer end = null;

        }

    }

    @Override
    public Type type() {
        return Type.CHAT_MESSAGE_V1;
    }

}
