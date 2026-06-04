package co.casterlabs.sdk.x.v1.requests;

import java.io.IOException;
import java.net.http.HttpResponse;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.x.Xv1Auth;
import co.casterlabs.sdk.x.v1.types.Xv1User;
import lombok.NonNull;

public class Xv1GetCurrentUserRequest extends AuthenticatedWebRequest<Xv1User, Xv1Auth> {

    public Xv1GetCurrentUserRequest(@NonNull Xv1Auth auth) {
        super(auth);
    }

    @Override
    protected Xv1User execute() throws ApiException, ApiAuthException, IOException {
        HttpResponse<JsonObject> response = _RequestHelper.GET(
            "https://api.x.com/1.1/account/verify_credentials.json",
            this.auth
        );
        System.out.println(response.body());
        return Rson.DEFAULT.fromJson(response.body(), Xv1User.class);
    }

}
