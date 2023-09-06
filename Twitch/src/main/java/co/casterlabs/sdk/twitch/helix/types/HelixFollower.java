package co.casterlabs.sdk.twitch.helix.types;

import java.io.IOException;
import java.time.Instant;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import co.casterlabs.sdk.twitch.helix.requests.HelixGetUsersRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@JsonClass(exposeAll = true)
public class HelixFollower {
    @JsonField("user_id")
    private @NonNull String id;

    @JsonField("followed_at")
    private @NonNull Instant followedAt;

    @Deprecated
    public HelixFollower() {} // For Rson

    public HelixUser getAsUser(@NonNull TwitchHelixAuth auth) throws ApiAuthException, ApiException, IOException {
        return new HelixGetUsersRequest(auth).addId(this.id).execute().get(0);
    }

}
