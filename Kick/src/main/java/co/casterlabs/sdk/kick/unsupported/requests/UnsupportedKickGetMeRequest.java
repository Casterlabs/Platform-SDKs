package co.casterlabs.sdk.kick.unsupported.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickApi;
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickAuth;
import co.casterlabs.sdk.kick.unsupported.types.UnsupportedKickUser;
import lombok.NonNull;

public class UnsupportedKickGetMeRequest extends AuthenticatedWebRequest<UnsupportedKickUser, UnsupportedKickAuth> {

    public UnsupportedKickGetMeRequest(@NonNull UnsupportedKickAuth auth) {
        super(auth);
    }

    @Override
    protected UnsupportedKickUser execute() throws ApiException, ApiAuthException, IOException {
        return WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(UnsupportedKickApi.API_BASE_URL + "/api/v1/user")),
            RsonBodyHandler.of(UnsupportedKickUser.class),
            this.auth
        ).body();
    }

}
