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
import co.casterlabs.sdk.kick.types.KickStreamInfo;
import lombok.NonNull;

public class KickGetStreamInfoRequest extends AuthenticatedWebRequest<KickStreamInfo, KickAuth> {

    public KickGetStreamInfoRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected KickStreamInfo execute() throws ApiException, ApiAuthException, IOException {
        return WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(KickApi.API_BASE_URL + "/stream/info"))
                .header("Accept", "application/json"),
            RsonBodyHandler.of(KickStreamInfo.class),
            this.auth
        ).body();
    }

}
