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
import co.casterlabs.sdk.twitch.helix.types.HelixEventSubSubscription;
import co.casterlabs.sdk.twitch.helix.types.HelixEventSubSubscription.HelixEventSubStatus;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TwitchHelixGetEventSubSubscriptionsRequest extends AuthenticatedWebRequest<PaginatedResponse<HelixEventSubSubscription>, TwitchHelixAuth> {
    private HelixEventSubStatus byStatus;
    private String byUserId;
    private String byType;

    /**
     * @param auth must be application auth if the subscription is a webhook or
     *             conduit, user auth otherwise.
     */
    public TwitchHelixGetEventSubSubscriptionsRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    @Override
    protected PaginatedResponse<HelixEventSubSubscription> execute() throws ApiException, ApiAuthException, IOException {
        return new PaginatedResponse<>(new _HelixPaginationHelper<>() {
            @Override
            protected Builder request(@Nullable String cursor) {
                String url = "https://api.twitch.tv/helix/eventsub/subscriptions?" + new URIParameters()
                    .optionalPut("status", byStatus == null ? null : byStatus.name().toLowerCase())
                    .optionalPut("user_id", byUserId)
                    .optionalPut("type", byType)
                    .optionalPut("after", cursor);
                return HttpRequest.newBuilder()
                    .uri(URI.create(url));
            }

            @Override
            protected TwitchHelixAuth auth() {
                return auth;
            }

            @Override
            protected Class<HelixEventSubSubscription[]> generic() {
                return HelixEventSubSubscription[].class;
            }
        });
    }

}
