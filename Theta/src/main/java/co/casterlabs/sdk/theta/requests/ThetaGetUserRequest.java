package co.casterlabs.sdk.theta.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.theta.ThetaAuth;
import co.casterlabs.sdk.theta.types.ThetaUser;
import lombok.NonNull;

public class ThetaGetUserRequest extends AuthenticatedWebRequest<ThetaUser, ThetaAuth> {
    private String query;

    public ThetaGetUserRequest(@NonNull ThetaAuth auth) {
        super(auth);
    }

    public ThetaGetUserRequest byId(String id) {
        this.query = String.format("/%s", id);
        return this;
    }

    public ThetaGetUserRequest byUsername(String username) {
        this.query = String.format("?username=%s", username);
        return this;
    }

    @Override
    protected ThetaUser execute() throws ApiException, ApiAuthException, IOException {
        String url = "https://api.theta.tv/v1/user" + this.query;

        JsonObject response = ThetaRequester.httpGet(url, this.auth);

        return Rson.DEFAULT.fromJson(response, ThetaUser.class);
    }

}
