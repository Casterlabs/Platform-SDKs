package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.PaginatedResponse;
import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import co.casterlabs.sdk.twitch.helix.types.HelixFollower;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TwitchHelixGetChannelFollowersRequest extends AuthenticatedWebRequest<PaginatedResponse<HelixFollower>, TwitchHelixAuth> {
    private @NonNull String forBroadcasterId;

    public TwitchHelixGetChannelFollowersRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    @Override
    protected PaginatedResponse<HelixFollower> execute() throws ApiException, ApiAuthException, IOException {
        assert !this.auth.isApplicationAuth() : "You cannot use Application Auth.";
        assert this.forBroadcasterId != null : "You must specify the broadcaster ID corresponding to the authenticated user";

        return new PaginatedResponse<>(new _HelixPaginationHelper<>() {
            @Override
            protected Builder request(@Nullable String cursor) {
                String url = "https://api.twitch.tv/helix/channels/followers?" + new QueryBuilder()
                    .put("broadcaster_id", forBroadcasterId);
                return HttpRequest.newBuilder()
                    .uri(URI.create(url));
            }

            @Override
            protected TwitchHelixAuth auth() {
                return auth;
            }

            @Override
            protected Class<HelixFollower[]> generic() {
                return HelixFollower[].class;
            }
        });
    }

}
