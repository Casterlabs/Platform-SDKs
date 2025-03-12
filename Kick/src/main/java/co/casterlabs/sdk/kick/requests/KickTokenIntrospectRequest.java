package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.kick.KickAuth;
import co.casterlabs.sdk.kick.types.KickIntrospection;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class KickTokenIntrospectRequest extends AuthenticatedWebRequest<KickIntrospection, KickAuth> {

    public KickTokenIntrospectRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected KickIntrospection execute() throws ApiException, ApiAuthException, IOException {
        String url = "https://api.kick.com/public/v1/token/introspect";

        return _KickApi.request(
            HttpRequest.newBuilder(URI.create(url)),
            this.auth,
            KickIntrospection.class
        );
    }

}
