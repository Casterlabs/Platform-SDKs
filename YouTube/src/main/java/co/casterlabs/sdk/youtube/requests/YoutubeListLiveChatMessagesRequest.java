package co.casterlabs.sdk.youtube.requests;

import java.io.IOException;

import org.jetbrains.annotations.Nullable;
import org.unbescape.uri.UriEscape;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.youtube.YoutubeAuth;
import co.casterlabs.sdk.youtube.YoutubeHttpUtil;
import co.casterlabs.sdk.youtube.types.YoutubeLiveChatMessagesList;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class YoutubeListLiveChatMessagesRequest extends AuthenticatedWebRequest<YoutubeLiveChatMessagesList, YoutubeAuth> {
    private @Setter @NonNull String liveChatId = null;
    private @Setter @Nullable String pageToken = null;

    public YoutubeListLiveChatMessagesRequest(@NonNull YoutubeAuth auth) {
        super(auth);
    }

    @Override
    protected YoutubeLiveChatMessagesList execute() throws ApiException, ApiAuthException, IOException {
        assert this.liveChatId != null : "You must specify a chat id.";

        String url = "https://youtube.googleapis.com/youtube/v3/liveChat/messages"
            + "?part=snippet%2CauthorDetails"
            + "&profileImageSize=88"
//            + "&maxResults=2000"
            + "&liveChatId=" + UriEscape.escapeUriQueryParam(this.liveChatId);

        if (this.pageToken != null) {
            url += "&pageToken=" + UriEscape.escapeUriQueryParam(this.pageToken);
        }

        JsonObject json = YoutubeHttpUtil.list(url, this.auth);
        json.put("isHistorical", this.pageToken == null);
        return Rson.DEFAULT.fromJson(json, YoutubeLiveChatMessagesList.class);
    }

}
