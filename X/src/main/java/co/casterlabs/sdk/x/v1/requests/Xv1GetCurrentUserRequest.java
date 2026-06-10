package co.casterlabs.sdk.x.v1.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.x.XSignedRequestHelper;
import co.casterlabs.sdk.x.XSignedRequestHelper.Response;
import co.casterlabs.sdk.x.Xv1Auth;
import co.casterlabs.sdk.x.v1.types.Xv1User;
import lombok.NonNull;

public class Xv1GetCurrentUserRequest extends AuthenticatedWebRequest<Xv1User, Xv1Auth> {

    public Xv1GetCurrentUserRequest(@NonNull Xv1Auth auth) {
        super(auth);
    }

    @Override
    protected Xv1User execute() throws ApiException, ApiAuthException, IOException {
        Response response = XSignedRequestHelper.GET(
            "https://api.x.com/1.1/account/verify_credentials.json",
            this.auth
        );
        return response.as(Xv1User.class);
    }

}
