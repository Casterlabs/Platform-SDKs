package co.casterlabs.sdk.x.v2.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.x.Xv2Auth;
import co.casterlabs.sdk.x.v2.types.Xv2StreamSource;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class Xv2CreateStreamSourceRequest extends AuthenticatedWebRequest<Xv2StreamSource, Xv2Auth> {
    private String userId;
    private String name;
    private String region;

    public Xv2CreateStreamSourceRequest(@NonNull Xv2Auth auth) {
        super(auth);
    }

    @Override
    protected Xv2StreamSource execute() throws ApiException, ApiAuthException, IOException {
        assert this.userId != null : "userId must be set";
        assert this.name != null : "name must be set";
        assert this.region != null : "region must be set";

        JsonObject body = new JsonObject()
            .put("name", this.name)
            .put("region", this.region);

        String url = String.format("https://api.x.com/2/users/%s/sources", this.userId);

        return _ApiHelper.request(
            HttpRequest.newBuilder(URI.create(url))
                .POST(BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json"),
            this.auth,
            ResponseHolder.class
        ).source;
    }

    @JsonClass(exposeAll = true)
    private static class ResponseHolder {
        public final Xv2StreamSource source = null;

    }

}
