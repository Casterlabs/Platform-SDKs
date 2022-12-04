package co.casterlabs.sdk.trovo.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.sdk.trovo.TrovoAuth;
import co.casterlabs.sdk.trovo.requests.data.TrovoSelfInfo;
import lombok.NonNull;
import okhttp3.Request;

public class TrovoGetSelfInfoRequest extends AuthenticatedWebRequest<TrovoSelfInfo, TrovoAuth> {
    public static final String URL = "https://open-api.trovo.live/openplatform/getuserinfo";

    public TrovoGetSelfInfoRequest(@NonNull TrovoAuth auth) {
        super(auth);
    }

    @Override
    protected TrovoSelfInfo execute() throws ApiException, ApiAuthException, IOException {
        assert !this.auth.isApplicationAuth() : "You must use user auth for this request.";

        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(URL),
            this.auth
        );

        return Rson.DEFAULT.fromJson(response, TrovoSelfInfo.class);
    }

}
