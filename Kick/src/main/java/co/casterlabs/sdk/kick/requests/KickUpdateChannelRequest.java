package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.KickAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true, fluent = true)
public class KickUpdateChannelRequest extends AuthenticatedWebRequest<Void, KickAuth> {
    private @Setter Integer withCategoryId = null;
    private @Setter String withStreamTitle = null;

    public KickUpdateChannelRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.withCategoryId != null : "You must specify a category id.";
        assert this.withStreamTitle != null && !this.withStreamTitle.isEmpty() : "You must specify a stream title.";

        String url = "https://api.kick.com/public/v1/channels";

        JsonObject payload = new JsonObject()
            .put("category_id", this.withCategoryId)
            .put("stream_title", this.withStreamTitle);

        _KickApi.request(
            HttpRequest.newBuilder(URI.create(url))
                .POST(BodyPublishers.ofString(payload.toString()))
                .header("Content-Type", "application/json"),
            this.auth
        );
        return null;
    }

}
