package co.casterlabs.sdk.kick.webhook.v1.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.sdk.kick.webhook.KickWebhookEvent;

@JsonClass(exposeAll = true)
public class KickWebhookLivestreamMetadataUpdatedV1 implements KickWebhookEvent {

    public final KickWebhookUserV1 broadcaster = null;

    public final Metadata metadata = null;

    @JsonClass(exposeAll = true)
    public static class Metadata {

        public final String title = null;

        public final String language = null;

        @JsonField("has_mature_content")
        public final Boolean hasMatureContent = null;

        public final Category category = null;

    }

    @JsonClass(exposeAll = true)
    public static class Category {

        public final Integer id = null;

        public final String name = null;

        public final String thumbnail = null;

    }

    @Override
    public Type type() {
        return Type.LIVESTREAM_METADATA_UPDATED_V1;
    }

}
