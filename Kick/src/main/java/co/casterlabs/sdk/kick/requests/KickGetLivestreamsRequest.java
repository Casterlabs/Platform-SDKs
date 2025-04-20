package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.URIParameters;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.sdk.kick.KickAuth;
import co.casterlabs.sdk.kick.types.KickLivestream;
import lombok.NonNull;
import lombok.Setter;

public class KickGetLivestreamsRequest extends AuthenticatedWebRequest<List<KickLivestream>, KickAuth> {
    private @Setter Integer forUserId = null;
    private @Setter Integer forCategoryId = null;
    private @Setter String forLanguage = null;

    private @Setter Integer limit = null;
    private String sort = null;

    public KickGetLivestreamsRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    public KickGetLivestreamsRequest forAnyone() {
        this.forUserId = null;
        return this;
    }

    public KickGetLivestreamsRequest forAnyCategory() {
        this.forCategoryId = null;
        return this;
    }

    public KickGetLivestreamsRequest forAnyLanguage() {
        this.forLanguage = null;
        return this;
    }

    public KickGetLivestreamsRequest anySorting() {
        this.sort = null;
        return this;
    }

    public KickGetLivestreamsRequest sortByViewers() {
        this.sort = "viewer_count";
        return this;
    }

    public KickGetLivestreamsRequest sortByStartTime() {
        this.sort = "started_at";
        return this;
    }

    @Override
    protected List<KickLivestream> execute() throws ApiException, ApiAuthException, IOException {
        String url = "https://api.kick.com/public/v1/livestreams?" + new URIParameters()
            .optionalPut("broadcaster_user_id", this.forUserId)
            .optionalPut("category_id", this.forCategoryId)
            .optionalPut("language", this.forLanguage)
            .optionalPut("limit", this.limit)
            .optionalPut("sort", this.sort);

        return _KickApi.request(
            HttpRequest.newBuilder(URI.create(url)),
            this.auth,
            new TypeToken<List<KickLivestream>>() {
            }
        );
    }

}
