package co.casterlabs.sdk.brime.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.brime.BrimeApi;
import co.casterlabs.sdk.brime.BrimeAuth;
import co.casterlabs.sdk.brime.types.BrimeUser;
import lombok.NonNull;
import okhttp3.Request;

public class BrimeGetAccountRequest extends AuthenticatedWebRequest<BrimeUser, BrimeAuth> {

    public BrimeGetAccountRequest(@NonNull BrimeAuth auth) {
        super(auth);
    }

    @Override
    protected BrimeUser execute() throws ApiException, ApiAuthException, IOException {
        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url("https://api.brime.tv/v1/account/me"),
            this.auth
        );

        return BrimeApi.RSON.fromJson(response, BrimeUser.class);
    }

}
