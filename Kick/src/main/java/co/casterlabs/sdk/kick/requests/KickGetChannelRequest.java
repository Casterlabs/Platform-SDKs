package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import org.unbescape.uri.UriEscape;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.kick.KickAuth;
import co.casterlabs.sdk.kick.types.KickChannel;
import lombok.NonNull;

public class KickGetChannelRequest extends AuthenticatedWebRequest<KickChannel, KickAuth> {
    private String query;

    public KickGetChannelRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    public KickGetChannelRequest forId(int id) {
        this.query = "?broadcaster_user_id=" + id;
        return this;
    }

    public KickGetChannelRequest forSlug(String slug) {
        this.query = "?slug=" + UriEscape.escapeUriQueryParam(slug);
        return this;
    }

    public KickGetChannelRequest me() {
        this.query = null;
        return this;
    }

    @Override
    protected KickChannel execute() throws ApiException, ApiAuthException, IOException {
        String url = "https://api.kick.com/public/v1/channels";
        if (this.query != null) {
            url += this.query;
        }

        return _KickApi.request(
            HttpRequest.newBuilder(URI.create(url)),
            this.auth,
            KickChannel[].class
        )[0];
    }

}
