package co.casterlabs.brimeapijava.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.types.BrimeChannel;
import okhttp3.Request;

public class BrimeGetChannelRequest extends WebRequest<BrimeChannel> {
    private String query;

    public BrimeGetChannelRequest queryByXid(String xid) {
        this.query = xid;
        return this;
    }

    public BrimeGetChannelRequest queryBySlug(String slug) {
        this.query = "slug/" + slug;
        return this;
    }

    @Override
    protected BrimeChannel execute() throws ApiException, ApiAuthException, IOException {
        String url = String.format("https://api.brime.tv/v1/channels/%s", this.query);

        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(url),
            null
        );

        return BrimeApi.RSON.fromJson(response, BrimeChannel.class);
    }

}
