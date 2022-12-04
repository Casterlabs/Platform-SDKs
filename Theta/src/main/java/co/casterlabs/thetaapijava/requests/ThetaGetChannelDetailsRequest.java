package co.casterlabs.thetaapijava.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.thetaapijava.ThetaAuth;
import co.casterlabs.thetaapijava.types.ThetaChannelDetails;
import lombok.NonNull;

public class ThetaGetChannelDetailsRequest extends AuthenticatedWebRequest<ThetaChannelDetails, ThetaAuth> {
    private String userId;

    public ThetaGetChannelDetailsRequest(@NonNull ThetaAuth auth) {
        super(auth);
    }

    public ThetaGetChannelDetailsRequest byId(String id) {
        this.userId = id;
        return this;
    }

    @Override
    protected ThetaChannelDetails execute() throws ApiException, ApiAuthException, IOException {
        String url = "https://api.theta.tv/v1/channel/" + this.userId;

        JsonObject response = ThetaRequester.httpGet(url, this.auth);

        return Rson.DEFAULT.fromJson(response, ThetaChannelDetails.class);
    }

}
