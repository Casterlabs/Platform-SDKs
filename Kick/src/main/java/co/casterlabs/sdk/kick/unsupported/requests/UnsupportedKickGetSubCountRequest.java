package co.casterlabs.sdk.kick.unsupported.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickApi;
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class UnsupportedKickGetSubCountRequest extends AuthenticatedWebRequest<Integer, UnsupportedKickAuth> {
    private @Setter String channelSlug;

    public UnsupportedKickGetSubCountRequest(@NonNull UnsupportedKickAuth auth) {
        super(auth);
    }

    @Override
    protected Integer execute() throws ApiException, ApiAuthException, IOException {
        assert this.channelSlug != null : "You must specify your channel slug.";

        JsonObject json = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(UnsupportedKickApi.API_BASE_URL + "/api/v2/channels/" + this.channelSlug + "/subscribers/last")),
            RsonBodyHandler.of(JsonObject.class),
            this.auth
        ).body();

        return json.getNumber("count").intValue();
    }

}
