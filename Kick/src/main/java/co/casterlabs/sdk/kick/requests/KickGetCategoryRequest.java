package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.kick.KickAuth;
import co.casterlabs.sdk.kick.types.KickCategory;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class KickGetCategoryRequest extends AuthenticatedWebRequest<KickCategory, KickAuth> {
    private @Setter Integer forId = null;

    public KickGetCategoryRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected KickCategory execute() throws ApiException, ApiAuthException, IOException {
        assert this.forId != null : "You must specify an id.";

        String url = "https://api.kick.com/public/v1/categories/" + this.forId;

        return _KickApi.request(
            HttpRequest.newBuilder(URI.create(url)),
            this.auth,
            KickCategory.class
        );
    }

}
