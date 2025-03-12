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
import co.casterlabs.sdk.kick.unsupported.types.UnsupportedKickPublishingInfo;
import lombok.NonNull;

public class UnsupportedKickGetStreamKeyRequest extends AuthenticatedWebRequest<UnsupportedKickPublishingInfo, UnsupportedKickAuth> {

    public UnsupportedKickGetStreamKeyRequest(@NonNull UnsupportedKickAuth auth) {
        super(auth);
    }

    @Override
    protected UnsupportedKickPublishingInfo execute() throws ApiException, ApiAuthException, IOException {
        return WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(UnsupportedKickApi.API_BASE_URL + "/stream/publish_token"))
                .header("Accept", "application/json"),
            RsonBodyHandler.of(UnsupportedKickPublishingInfo.class),
            this.auth
        ).body();
    }

}
