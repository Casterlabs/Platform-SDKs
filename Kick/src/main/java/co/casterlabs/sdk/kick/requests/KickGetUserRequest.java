package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import org.unbescape.uri.UriEscape;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.kick.KickAuth;
import co.casterlabs.sdk.kick.types.KickUser;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class KickGetUserRequest extends AuthenticatedWebRequest<KickUser, KickAuth> {
    private @Setter Integer forId = null;

    public KickGetUserRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    public KickGetUserRequest me() {
        this.forId = null;
        return this;
    }

    @Override
    protected KickUser execute() throws ApiException, ApiAuthException, IOException {
        String url = "https://api.kick.com/public/v1/users";
        if (this.forId != null) {
            url += "?id=" + UriEscape.escapeUriQueryParam(String.valueOf(this.forId));
        }

        return _KickApi.request(
            HttpRequest.newBuilder(URI.create(url)),
            this.auth,
            KickUser[].class
        )[0];
    }

}
