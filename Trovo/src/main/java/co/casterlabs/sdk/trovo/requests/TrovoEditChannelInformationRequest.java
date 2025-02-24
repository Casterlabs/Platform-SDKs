package co.casterlabs.sdk.trovo.requests;

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
import co.casterlabs.sdk.trovo.TrovoAuth;
import co.casterlabs.sdk.trovo.requests.data.TrovoAudienceType;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TrovoEditChannelInformationRequest extends AuthenticatedWebRequest<Void, TrovoAuth> {
    public static final String URL = "https://open-api.trovo.live/openplatform/channels/update";

    private String forChannelId;
    private String withTitle;
    private String withCategory;
    private String withLanguageCode;
    private TrovoAudienceType withAudienceType;

    public TrovoEditChannelInformationRequest(@NonNull TrovoAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.forChannelId != null : "You must set a channel id.";
        assert this.withTitle != null : "You must set a title.";
        assert this.withCategory != null : "You must set a category.";
        assert this.withLanguageCode != null : "You must set a language.";
        assert this.withAudienceType != null : "You must set a audience type.";
        assert !this.auth.isApplicationAuth() : "You must use user auth for this request.";

        JsonObject body = new JsonObject()
            .put("channel_id", this.forChannelId)
            .put("live_title", this.withTitle)
            .put("category", this.withCategory)
            .put("language_code", this.withLanguageCode)
            .put("audi_type", this.withAudienceType.name());

        WebRequest.sendHttpRequest(
            HttpRequest.newBuilder(URI.create(URL))
                .POST(BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json"),
            BodyHandlers.discarding(),
            this.auth
        );

        return null;
    }

}
