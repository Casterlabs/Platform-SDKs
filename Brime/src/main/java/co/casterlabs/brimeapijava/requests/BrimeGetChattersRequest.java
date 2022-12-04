package co.casterlabs.brimeapijava.requests;

import java.io.IOException;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.types.BrimeChatter;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.element.JsonObject;
import okhttp3.Request;

public class BrimeGetChattersRequest extends WebRequest<List<BrimeChatter>> {
    private static final TypeToken<List<BrimeChatter>> LIST_TYPE = new TypeToken<List<BrimeChatter>>() {
    };

    private String query;

    public BrimeGetChattersRequest queryByXid(String xid) {
        this.query = xid;
        return this;
    }

    @Override
    protected List<BrimeChatter> execute() throws ApiException, ApiAuthException, IOException {
        String url = String.format("https://api.brime.tv/v1/chat/channel/%s/chatters", this.query);

        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(url),
            null
        );

        JsonObject json = BrimeApi.RSON.fromJson(response, JsonObject.class);

        return BrimeApi.RSON.fromJson(json.get("chatters"), LIST_TYPE);
    }

}
