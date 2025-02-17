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
import co.casterlabs.apiutil.web.URIParameters;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import co.casterlabs.sdk.twitch.helix.types.HelixSubscriber;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TwitchHelixGetChannelSubscribersRequest extends AuthenticatedWebRequest<PaginatedResponse<HelixSubscriber>, TwitchHelixAuth> {
    private @NonNull String forBroadcasterId;

    public TwitchHelixGetChannelSubscribersRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    @Override
    protected PaginatedResponse<HelixSubscriber> execute() throws ApiException, ApiAuthException, IOException {
        assert !this.auth.isApplicationAuth() : "You cannot use Application Auth.";
        assert this.forBroadcasterId != null : "You must specify the broadcaster ID corresponding to the authenticated user";

        return new PaginatedResponse<>(new _HelixPaginationHelper<>() {
            @Override
            protected Builder request(@Nullable String cursor) {
                String url = "https://api.twitch.tv/helix/subscriptions?" + new URIParameters()
                    .put("broadcaster_id", forBroadcasterId);
                return HttpRequest.newBuilder()
                    .uri(URI.create(url));
            }

            @Override
            protected TwitchHelixAuth auth() {
                return auth;
            }

            @Override
            protected Class<HelixSubscriber[]> generic() {
                return HelixSubscriber[].class;
            }
        });
    }

}
