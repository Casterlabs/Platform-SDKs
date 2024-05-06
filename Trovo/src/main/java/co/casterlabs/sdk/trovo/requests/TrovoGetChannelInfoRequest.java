package co.casterlabs.sdk.trovo.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.trovo.TrovoAuth;
import co.casterlabs.sdk.trovo.requests.data.TrovoChannelInfo;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@NonNull
@Accessors(chain = true)
public class TrovoGetChannelInfoRequest extends AuthenticatedWebRequest<TrovoChannelInfo, TrovoAuth> {
    private static final String URL = "https://open-api.trovo.live/openplatform/channels/id";
    private static final String SELF_URL = "https://open-api.trovo.live/openplatform/channel";

    private @Nullable String channelId;

    public TrovoGetChannelInfoRequest(@NonNull TrovoAuth auth) {
        super(auth);
    }

    @Override
    protected TrovoChannelInfo execute() throws ApiException, ApiAuthException, IOException {
        HttpRequest.Builder request = HttpRequest.newBuilder();

        if (this.channelId == null) {
            assert !this.auth.isApplicationAuth() : "You cannot use application auth to request self info. Set a channel id or use user auth.";

            request.uri(URI.create(SELF_URL));
        } else {
            JsonObject body = new JsonObject()
                .put("channel_id", this.channelId);

            request
                .uri(URI.create(URL))
                .POST(BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json");
        }

        return WebRequest.sendHttpRequest(
            request,
            RsonBodyHandler.of(TrovoChannelInfo.class),
            this.auth
        ).body();
    }

}
