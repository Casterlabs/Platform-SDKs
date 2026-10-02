package co.casterlabs.sdk.x.v2.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.sdk.x.Xv2Auth;
import co.casterlabs.sdk.x.v2.types.Xv2StreamSource;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class Xv2GetStreamSourceRequest extends AuthenticatedWebRequest<Xv2StreamSource, Xv2Auth> {
    private String userId;
    private String sourceId;

    public Xv2GetStreamSourceRequest(@NonNull Xv2Auth auth) {
        super(auth);
    }

    @Override
    protected Xv2StreamSource execute() throws ApiException, ApiAuthException, IOException {
        assert this.userId != null : "userId must be set";
        assert this.sourceId != null : "sourceId must be set";

        String url = String.format("https://api.x.com/2/users/%s/sources/%s", this.userId, this.sourceId);

        return _ApiHelper.request(
            HttpRequest.newBuilder(URI.create(url)),
            this.auth,
            ResponseHolder.class
        ).source;
    }

    @JsonClass(exposeAll = true)
    private static class ResponseHolder {
        public final Xv2StreamSource source = null;

    }

}
