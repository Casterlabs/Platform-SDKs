package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.kick.KickApi;
import co.casterlabs.sdk.kick.KickAuth;
import co.casterlabs.sdk.kick.types.KickUser;
import lombok.NonNull;

public class KickGetMeRequest extends AuthenticatedWebRequest<KickUser, KickAuth> {

    public KickGetMeRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected KickUser execute() throws ApiException, ApiAuthException, IOException {
        return WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(KickApi.API_BASE_URL + "/api/v1/user")),
            RsonBodyHandler.of(KickUser.class),
            this.auth
        ).body();
    }

}
