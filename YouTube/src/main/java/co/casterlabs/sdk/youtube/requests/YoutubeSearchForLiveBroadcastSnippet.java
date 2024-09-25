package co.casterlabs.sdk.youtube.requests;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.unbescape.uri.UriEscape;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.sdk.youtube.YoutubeAuth;
import co.casterlabs.sdk.youtube.YoutubeHttpUtil;
import co.casterlabs.sdk.youtube.types.YoutubeLiveBroadcastData;
import lombok.NonNull;

public class YoutubeSearchForLiveBroadcastSnippet extends AuthenticatedWebRequest<List<YoutubeLiveBroadcastData.LiveBroadcastSnippet>, YoutubeAuth> {
    private String channelId = null;

    public YoutubeSearchForLiveBroadcastSnippet(@NonNull YoutubeAuth auth) {
        super(auth);
    }

    public YoutubeSearchForLiveBroadcastSnippet byId(@NonNull String channelId) {
        this.channelId = channelId;
        return this;
    }

    @Override
    protected List<YoutubeLiveBroadcastData.LiveBroadcastSnippet> execute() throws ApiException, ApiAuthException, IOException {
        assert this.channelId != null : "You must specify the ID of the channel to search.";

        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet"
            + "&channelId=" + UriEscape.escapeUriQueryParam(this.channelId)
            + "&type=video"
            + "&eventType=live";

        JsonObject json = YoutubeHttpUtil.list(url, this.auth);
        JsonArray items = json.getArray("items");

        if (items.isEmpty()) {
            return null;
        }

        return items.toList()
            .stream()
            .map((e) -> e.getAsObject())
            .map((item) -> {
                JsonObject snippet = item.getObject("snippet");

                // Inject the ID into the snippet.
                snippet.put("id", item.getObject("id").get("videoId"));

                try {
                    return Rson.DEFAULT.fromJson(
                        snippet,
                        YoutubeLiveBroadcastData.LiveBroadcastSnippet.class
                    );
                } catch (JsonParseException ex) {
                    throw new RuntimeException(ex);
                }
            })
            .collect(Collectors.toList());
    }

}
