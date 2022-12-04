package co.casterlabs.sdk.trovo.requests;

import java.io.IOException;

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
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

@Setter
@NonNull
@Accessors(chain = true)
public class TrovoEditChannelInformationRequest extends AuthenticatedWebRequest<Void, TrovoAuth> {
    public static final String URL = "https://open-api.trovo.live/openplatform/channels/update";

    private String channelId;
    private String title;
    private String category;
    private String languageCode;
    private TrovoAudienceType audienceType;

    public TrovoEditChannelInformationRequest(@NonNull TrovoAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.channelId != null : "You must set a channel id.";
        assert this.title != null : "You must set a title.";
        assert this.category != null : "You must set a category.";
        assert this.languageCode != null : "You must set a language.";
        assert this.audienceType != null : "You must set a audience type.";
        assert !this.auth.isApplicationAuth() : "You must use user auth for this request.";

        JsonObject body = new JsonObject()
            .put("channel_id", this.channelId)
            .put("live_title", this.title)
            .put("category", this.category)
            .put("language_code", this.languageCode)
            .put("audi_type", this.audienceType.name());

        WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(URL)
                .post(
                    RequestBody.create(
                        body.toString(),
                        MediaType.get("application/json")
                    )
                ),
            this.auth
        );

        return null;
    }

}
