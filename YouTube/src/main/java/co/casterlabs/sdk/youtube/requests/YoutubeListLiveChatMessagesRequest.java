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

@Setter
@Accessors(chain = true, fluent = true)
public class YoutubeListLiveChatMessagesRequest extends AuthenticatedWebRequest<YoutubeLiveChatMessagesList, YoutubeAuth> {
    private @NonNull String forLiveChatId = null;
    private @Nullable String withPageToken = null;

    public YoutubeListLiveChatMessagesRequest(@NonNull YoutubeAuth auth) {
        super(auth);
    }

    @Override
    protected YoutubeLiveChatMessagesList execute() throws ApiException, ApiAuthException, IOException {
        assert this.forLiveChatId != null : "You must specify a chat id.";

        String url = "https://youtube.googleapis.com/youtube/v3/liveChat/messages"
            + "?part=snippet%2CauthorDetails"
            + "&profileImageSize=88"
//            + "&maxResults=2000"
            + "&liveChatId=" + UriEscape.escapeUriQueryParam(this.forLiveChatId);

        if (this.withPageToken != null) {
            url += "&pageToken=" + UriEscape.escapeUriQueryParam(this.withPageToken);
        }

        JsonObject json = YoutubeHttpUtil.list(url, this.auth);
        json.put("isHistorical", this.withPageToken == null);
        return Rson.DEFAULT.fromJson(json, YoutubeLiveChatMessagesList.class);
    }

}
