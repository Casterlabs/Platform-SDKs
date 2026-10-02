package co.casterlabs.sdk.x.v2.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.x.Xv2Auth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class Xv2DeleteStreamSourceRequest extends AuthenticatedWebRequest<Void, Xv2Auth> {
    private String userId;
    private String sourceId;

    public Xv2DeleteStreamSourceRequest(@NonNull Xv2Auth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.userId != null : "userId must be set";
        assert this.sourceId != null : "sourceId must be set";

        String url = String.format("https://api.x.com/2/users/%s/sources/%s", this.userId, this.sourceId);

        _ApiHelper.request(
            HttpRequest.newBuilder(URI.create(url))
                .DELETE(),
            this.auth
        );
        return null;
    }

}
