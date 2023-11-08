package co.casterlabs.sdk.kick.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.kick.KickApi;
import co.casterlabs.sdk.kick.KickAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Request;

@Accessors(chain = true)
public class KickDeletePollRequest extends AuthenticatedWebRequest<Void, KickAuth> {
    private @Setter String channelSlug;

    public KickDeletePollRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.channelSlug != null : "You must specify a channel slug.";

        WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(KickApi.API_BASE_URL + "/api/v2/channels/" + this.channelSlug + "/polls")
                .delete()
                .header("Accept", "application/json"),
            this.auth
        );

        return null;
    }

}
