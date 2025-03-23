package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import org.unbescape.uri.UriEscape;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.kick.KickAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true, fluent = true)
public class KickDeleteWebhookRequest extends AuthenticatedWebRequest<Void, KickAuth> {
    private @Setter String forId = null;

    public KickDeleteWebhookRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.forId != null : "You must specify an id.";

        String url = "https://api.kick.com/public/v1/events/subscriptions?id=" + UriEscape.escapeUriQueryParam(this.forId);

        _KickApi.request(
            HttpRequest.newBuilder(URI.create(url))
                .DELETE(),
            this.auth
        );
        return null;
    }

}
