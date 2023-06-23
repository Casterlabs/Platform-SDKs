package co.casterlabs.sdk.kick.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.sdk.kick.KickApi;
import co.casterlabs.sdk.kick.KickAuth;
import co.casterlabs.sdk.kick.types.KickStreamInfo;
import lombok.NonNull;
import okhttp3.Request;

public class KickGetStreamInfoRequest extends AuthenticatedWebRequest<KickStreamInfo, KickAuth> {

    public KickGetStreamInfoRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected KickStreamInfo execute() throws ApiException, ApiAuthException, IOException {
        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(KickApi.API_BASE_URL + "/stream/info")
                .header("Accept", "application/json"),
            this.auth
        );

        return Rson.DEFAULT.fromJson(response, KickStreamInfo.class);
    }

}
