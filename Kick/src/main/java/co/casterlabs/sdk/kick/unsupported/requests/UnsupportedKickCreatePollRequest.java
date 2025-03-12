package co.casterlabs.sdk.kick.unsupported.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickApi;
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class UnsupportedKickCreatePollRequest extends AuthenticatedWebRequest<Void, UnsupportedKickAuth> {
    private @Setter String channelSlug;
    private @Setter String title;
    private @Setter String[] options = {};
    private @Setter int duration = 30;
    private @Setter int resultDisplayDuration = 60;

    public UnsupportedKickCreatePollRequest(@NonNull UnsupportedKickAuth auth) {
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
            HttpRequest.newBuilder()
                .uri(URI.create(UnsupportedKickApi.API_BASE_URL + "/api/v2/channels/" + this.channelSlug + "/polls"))
                .POST(BodyPublishers.ofString(payload.toString()))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json"),
            BodyHandlers.discarding(),
            this.auth
        );

        return null;
    }

}
