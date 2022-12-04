package co.casterlabs.youtubeapijava.requests;

import java.io.IOException;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.youtubeapijava.HttpUtil;
import co.casterlabs.youtubeapijava.YoutubeApi;
import co.casterlabs.youtubeapijava.YoutubeAuth;
import co.casterlabs.youtubeapijava.types.YoutubeLiveChatMessagesList;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

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
            + "&liveChatId=" + HttpUtil.encodeURIComponent(this.liveChatId);

        if (this.pageToken != null) {
            url += "&pageToken=" + HttpUtil.encodeURIComponent(this.pageToken);
        }

        try (Response response = HttpUtil.sendHttpGet(url, this.auth)) {
            String body = response.body().string();

            if (response.isSuccessful()) {
                JsonObject json = YoutubeApi.RSON.fromJson(body, JsonObject.class);

                json.put("isHistorical", this.pageToken == null);

                return YoutubeApi.RSON.fromJson(json, YoutubeLiveChatMessagesList.class);
            } else {
                throw new ApiException(body);
            }
        } catch (JsonParseException e) {
            throw new ApiException(e);
        }
    }

}
