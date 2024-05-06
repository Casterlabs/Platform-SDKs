package co.casterlabs.sdk.trovo.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.trovo.TrovoAuth;
import co.casterlabs.sdk.trovo.requests.data.TrovoSelfInfo;
import lombok.NonNull;

public class TrovoGetSelfInfoRequest extends AuthenticatedWebRequest<TrovoSelfInfo, TrovoAuth> {
    public static final String URL = "https://open-api.trovo.live/openplatform/getuserinfo";

    public TrovoGetSelfInfoRequest(@NonNull TrovoAuth auth) {
        super(auth);
    }

    @Override
    protected TrovoSelfInfo execute() throws ApiException, ApiAuthException, IOException {
        assert !this.auth.isApplicationAuth() : "You must use user auth for this request.";

        return WebRequest.sendHttpRequest(
            HttpRequest.newBuilder(URI.create(URL)),
            RsonBodyHandler.of(TrovoSelfInfo.class),
            this.auth
        ).body();
    }

}
