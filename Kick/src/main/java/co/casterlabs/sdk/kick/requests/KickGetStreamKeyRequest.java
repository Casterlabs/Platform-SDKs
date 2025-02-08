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
import co.casterlabs.sdk.kick.types.KickPublishingInfo;
import lombok.NonNull;

public class KickGetStreamKeyRequest extends AuthenticatedWebRequest<KickPublishingInfo, KickAuth> {

    public KickGetStreamKeyRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected KickPublishingInfo execute() throws ApiException, ApiAuthException, IOException {
        return WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(KickApi.API_BASE_URL + "/stream/publish_token"))
                .header("Accept", "application/json"),
            RsonBodyHandler.of(KickPublishingInfo.class),
            this.auth
        ).body();
    }

}
