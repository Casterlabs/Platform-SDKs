package co.casterlabs.sdk.youtube.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.sdk.youtube.HttpUtil;
import co.casterlabs.sdk.youtube.YoutubeApi;
import co.casterlabs.sdk.youtube.YoutubeAuth;
import co.casterlabs.sdk.youtube.types.YoutubeLiveBroadcastData;
import lombok.NonNull;
import okhttp3.Response;

public class YoutubeSearchForLiveBroadcastSnippet extends AuthenticatedWebRequest<YoutubeLiveBroadcastData.LiveBroadcastSnippet, YoutubeAuth> {
    private String channelId = null;

    public YoutubeSearchForLiveBroadcastSnippet(@NonNull YoutubeAuth auth) {
        super(auth);
    }

    public YoutubeSearchForLiveBroadcastSnippet byId(@NonNull String channelId) {
        this.channelId = channelId;
        return this;
    }

    @Override
    protected YoutubeLiveBroadcastData.LiveBroadcastSnippet execute() throws ApiException, ApiAuthException, IOException {
        assert this.channelId != null : "You must specify the ID of the channel to search.";

        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet"
            + "&channelId=" + HttpUtil.encodeURIComponent(this.channelId)
            + "&type=video"
            + "&eventType=live";

        try (Response response = HttpUtil.sendHttpGet(url, this.auth)) {
            String body = response.body().string();

            if (response.isSuccessful()) {
                JsonObject json = Rson.DEFAULT.fromJson(body, JsonObject.class);
                JsonArray items = json.getArray("items");

                if (items.isEmpty()) {
                    return null;
                }

                JsonObject item = items.getObject(0);
                JsonObject snippet = item.getObject("snippet");

                // Inject the ID into the snippet.
                snippet.put("id", item.getObject("id").get("videoId"));

                return YoutubeApi.RSON.fromJson(
                    snippet,
                    YoutubeLiveBroadcastData.LiveBroadcastSnippet.class
                );
            } else {
                throw new ApiException(body);
            }
        } catch (JsonParseException e) {
            throw new ApiException(e);
        }
    }

}
