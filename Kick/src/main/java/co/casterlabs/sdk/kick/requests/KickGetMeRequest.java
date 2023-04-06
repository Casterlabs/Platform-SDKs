package co.casterlabs.sdk.kick.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.sdk.kick.KickApi;
import co.casterlabs.sdk.kick.KickAuth;
import co.casterlabs.sdk.kick.types.KickUser;
import lombok.NonNull;
import okhttp3.Request;

public class KickGetMeRequest extends AuthenticatedWebRequest<KickUser, KickAuth> {

    public KickGetMeRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected KickUser execute() throws ApiException, ApiAuthException, IOException {
        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(KickApi.API_BASE_URL + "/api/v1/user"),
            this.auth
        );

        return Rson.DEFAULT.fromJson(response, KickUser.class);
    }

}
