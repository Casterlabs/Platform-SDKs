package co.casterlabs.sdk.kick.unsupported.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickApi;
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class UnsupportedKickDeletePollRequest extends AuthenticatedWebRequest<Void, UnsupportedKickAuth> {
    private @Setter String channelSlug;

    public UnsupportedKickDeletePollRequest(@NonNull UnsupportedKickAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.channelSlug != null : "You must specify a channel slug.";

        WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(UnsupportedKickApi.API_BASE_URL + "/api/v2/channels/" + this.channelSlug + "/polls"))
                .DELETE()
                .header("Accept", "application/json"),
            BodyHandlers.discarding(),
            this.auth
        );

        return null;
    }

}
