package co.casterlabs.sdk.brime.requests;

import java.io.IOException;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.brime.BrimeApi;
import co.casterlabs.sdk.brime.types.BrimeUser;
import okhttp3.Request;

public class BrimeGetUserRequest extends WebRequest<BrimeUser> {

    private String query;

    public BrimeGetUserRequest queryByXid(String xid) {
        this.query = xid;
        return this;
    }

    @Override
    protected BrimeUser execute() throws ApiException, IOException {
        String url = String.format("https://api.brime.tv/v1/users/%s", this.query);

        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(url),
            null
        );

        return BrimeApi.RSON.fromJson(response, BrimeUser.class);
    }

}
