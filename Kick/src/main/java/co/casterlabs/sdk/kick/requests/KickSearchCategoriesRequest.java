package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;

import org.unbescape.uri.UriEscape;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.kick.KickAuth;
import co.casterlabs.sdk.kick.types.KickCategory;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true, fluent = true)
public class KickSearchCategoriesRequest extends AuthenticatedWebRequest<List<KickCategory>, KickAuth> {
    private @Setter String withQuery;

    public KickSearchCategoriesRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected List<KickCategory> execute() throws ApiException, ApiAuthException, IOException {
        assert this.withQuery != null : "You must specify a query.";

        String url = "https://api.kick.com/public/v1/categories?q=" + UriEscape.escapeUriQueryParam(this.withQuery);

        return _KickApi.request(
            HttpRequest.newBuilder(URI.create(url)),
            this.auth,
            KickCategory.LIST_TYPE
        );
    }

}
