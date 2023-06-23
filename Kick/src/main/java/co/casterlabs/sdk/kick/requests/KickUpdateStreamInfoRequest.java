package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

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
            new Request.Builder()
                .url(KickApi.API_BASE_URL + "/stream/info")
                .put(RequestBody.create(payload.toString().getBytes(StandardCharsets.UTF_8), MediaType.parse("application/json")))
                .header("Accept", "application/json"),
            this.auth
        );

        return null;
    }

}
