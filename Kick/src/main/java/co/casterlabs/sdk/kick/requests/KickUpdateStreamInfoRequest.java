package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.KickApi;
import co.casterlabs.sdk.kick.KickAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class KickUpdateStreamInfoRequest extends AuthenticatedWebRequest<Void, KickAuth> {
    private @Setter boolean isMature = false;
    private @Setter String language = "English";
    private @Setter long category = 16;
    private @Setter String title = "Test";

    public KickUpdateStreamInfoRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        JsonObject payload = new JsonObject()
            .put("is_mature", this.isMature)
            .put("language", this.language)
            .put("subcategoryId", this.category)
            .put("title", this.title);

        WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(KickApi.API_BASE_URL + "/stream/info"))
                .PUT(BodyPublishers.ofString(payload.toString()))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json"),
            BodyHandlers.discarding(),
            this.auth
        );

        return null;
    }

}
