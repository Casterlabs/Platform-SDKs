package co.casterlabs.sdk.kick.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.KickApi;
import co.casterlabs.sdk.kick.KickAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Request;

@Accessors(chain = true)
public class KickGetSubCountRequest extends AuthenticatedWebRequest<Integer, KickAuth> {
    private @Setter String channelSlug;

    public KickGetSubCountRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected Integer execute() throws ApiException, ApiAuthException, IOException {
        assert this.channelSlug != null : "You must specify your channel slug.";

        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(KickApi.API_BASE_URL + "/api/v2/channels/" + this.channelSlug + "/subscribers/last"),
            auth
        );

        JsonObject json = Rson.DEFAULT.fromJson(response, JsonObject.class);

        return json.getNumber("count").intValue();
    }

}
