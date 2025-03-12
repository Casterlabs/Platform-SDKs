package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.sdk.kick.KickAuth;
import co.casterlabs.sdk.kick.types.KickWebhook;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class KickGetWebhooksRequest extends AuthenticatedWebRequest<List<KickWebhook>, KickAuth> {

    public KickGetWebhooksRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected List<KickWebhook> execute() throws ApiException, ApiAuthException, IOException {
        String url = "https://api.kick.com/public/v1/events/subscriptions";

        return _KickApi.request(
            HttpRequest.newBuilder(URI.create(url)),
            this.auth,
            new TypeToken<List<KickWebhook>>() {
            }
        );
    }

}
