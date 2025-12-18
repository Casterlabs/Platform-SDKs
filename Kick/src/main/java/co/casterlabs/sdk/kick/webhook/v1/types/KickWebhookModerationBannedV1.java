package co.casterlabs.sdk.kick.webhook.v1.types;

import java.time.Instant;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.sdk.kick.webhook.KickWebhookEvent;

@JsonClass(exposeAll = true)
public class KickWebhookModerationBannedV1 implements KickWebhookEvent {

    public final KickWebhookUserV1 broadcaster = null;

    public final KickWebhookUserV1 moderator = null;

    @JsonField("banned_user")
    public final KickWebhookUserV1 bannedUser = null;

    public final Metadata metadata = null;

    @JsonClass(exposeAll = true)
    public static class Metadata {

        public final String reason = null;

        @JsonField("created_at")
        public final Instant createdAt = null;

        /**
         * Null if the ban is permanent.
         */
        @JsonField("expires_at")
        public final @Nullable Instant expiresAt = null;

    }

    @Override
    public Type type() {
        return Type.MODERATION_BANNED_V1;
    }

}
