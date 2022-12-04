package co.casterlabs.brimeapijava.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.BrimeAuth;
import co.casterlabs.brimeapijava.types.BrimeUser;
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
