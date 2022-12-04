package co.casterlabs.trovoapi.requests;

import java.io.IOException;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.trovoapi.TrovoAuth;
import co.casterlabs.trovoapi.requests.data.TrovoChannelInfo;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

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
        Request.Builder request = new Request.Builder();

        if (this.channelId == null) {
            assert !this.auth.isApplicationAuth() : "You cannot use application auth to request self info. Set a channel id or use user auth.";

            request.url(SELF_URL);
        } else {
            JsonObject body = new JsonObject()
                .put("channel_id", this.channelId);

            request
                .url(URL)
                .post(
                    RequestBody.create(
                        body.toString(),
                        MediaType.get("application/json")
                    )
                );
        }

        String response = WebRequest.sendHttpRequest(request, this.auth);

        return Rson.DEFAULT.fromJson(response, TrovoChannelInfo.class);
    }

}
