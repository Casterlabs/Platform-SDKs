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
import co.casterlabs.sdk.kick.unsupported.types.UnsupportedKickStreamInfo;
import lombok.NonNull;

public class UnsupportedKickGetStreamInfoRequest extends AuthenticatedWebRequest<UnsupportedKickStreamInfo, UnsupportedKickAuth> {

    public UnsupportedKickGetStreamInfoRequest(@NonNull UnsupportedKickAuth auth) {
        super(auth);
    }

    @Override
    protected UnsupportedKickStreamInfo execute() throws ApiException, ApiAuthException, IOException {
        return WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(UnsupportedKickApi.API_BASE_URL + "/stream/info"))
                .header("Accept", "application/json"),
            RsonBodyHandler.of(UnsupportedKickStreamInfo.class),
            this.auth
        ).body();
    }

}
