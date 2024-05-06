package co.casterlabs.sdk.trovo.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.sdk.trovo.TrovoAuth;
import co.casterlabs.sdk.trovo.requests.TrovoGetChannelFollowersRequest.TrovoGetChannelFollowersResponse;
import co.casterlabs.sdk.trovo.requests.data.TrovoFollower;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@NonNull
@Accessors(chain = true)
public class TrovoGetChannelFollowersRequest extends AuthenticatedWebRequest<TrovoGetChannelFollowersResponse, TrovoAuth> {
    public static final String URL = "https://open-api.trovo.live/openplatform/channels/%s/followers";

    private String channelId;

    public TrovoGetChannelFollowersRequest(@NonNull TrovoAuth auth) {
        super(auth);
    }

    @Override
    protected TrovoGetChannelFollowersResponse execute() throws IOException, ApiException {
        assert this.channelId != null : "You must set a channel id.";

        return WebRequest.sendHttpRequest(
            HttpRequest.newBuilder(URI.create(String.format(URL, this.channelId))),
            RsonBodyHandler.of(TrovoGetChannelFollowersResponse.class),
            this.auth
        ).body();
    }

    @Getter
    @NonNull
    @ToString
    @JsonClass(exposeAll = true)
    public static class TrovoGetChannelFollowersResponse {
        private long total;
        private List<TrovoFollower> follower;

    }

}
