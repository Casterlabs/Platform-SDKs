package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
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
public class KickCreatePollRequest extends AuthenticatedWebRequest<Void, KickAuth> {
    private @Setter String channelSlug;
    private @Setter String title;
    private @Setter String[] options = {};
    private @Setter int duration = 30;
    private @Setter int resultDisplayDuration = 60;

    public KickCreatePollRequest(@NonNull KickAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.duration >= 30 && this.duration <= 300 : "Poll duration must be between 30 seconds and 5 minutes in length.";
        assert this.resultDisplayDuration >= 15 && this.duration <= 300 : "Poll resultDisplayDuration must be between 15 seconds and 5 minutes in length.";
        assert this.options != null && this.options.length >= 2 && this.options.length <= 6 : "Poll options must have between 2 and 6 (inclusive) options.";
        assert this.title != null && !this.title.isEmpty() : "Poll title cannot be empty.";
        assert this.channelSlug != null : "You must specify a channel slug.";

        JsonObject payload = new JsonObject()
            .put("title", this.title)
            .put("options", Rson.DEFAULT.toJson(this.options))
            .put("duration", this.duration)
            .put("result_display_duration", this.resultDisplayDuration);

        WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(KickApi.API_BASE_URL + "/api/v2/channels/" + this.channelSlug + "/polls")
                .post(RequestBody.create(payload.toString().getBytes(StandardCharsets.UTF_8), MediaType.parse("application/json")))
                .header("Accept", "application/json"),
            this.auth
        );

        return null;
    }

}
