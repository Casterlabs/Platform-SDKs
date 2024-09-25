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

public class YoutubeGetLiveBroadcastRequest extends AuthenticatedWebRequest<List<YoutubeLiveBroadcastData>, YoutubeAuth> {
    private int queryMode = -1; // id, mine
    private String queryData = null;

    public YoutubeGetLiveBroadcastRequest(@NonNull YoutubeAuth auth) {
        super(auth);
    }

    public YoutubeGetLiveBroadcastRequest byId(@NonNull String videoId) {
        this.queryMode = 0;
        this.queryData = videoId;
        return this;
    }

    public YoutubeGetLiveBroadcastRequest mine() {
        this.queryMode = 1;
        this.queryData = null;
        return this;
    }

    @Override
    protected List<YoutubeLiveBroadcastData> execute() throws ApiException, ApiAuthException, IOException {
        assert this.queryMode != -1 : "You must specify a query either by ID or mine.";

        String url = "https://youtube.googleapis.com/youtube/v3/liveBroadcasts"
            + "?part=snippet,status";

        switch (this.queryMode) {
            case 0: {
                url += String.format("&id=%s", UriEscape.escapeUriQueryParam(this.queryData));
                break;
            }

            case 2: {
                if (this.auth.isApplicationAuth()) {
                    throw new ApiAuthException("You must use user auth when requesting `mine()`");
                }

                url += "&broadcastStatus=all&mine=true";
                break;
            }
        }

        JsonObject json = YoutubeHttpUtil.list(url, this.auth);
        JsonArray items = json.getArray("items");

        if (items.isEmpty()) {
            return null;
        }

        return items.toList()
            .stream()
            .map((e) -> e.getAsObject())
            .map((item) -> {
                // Inject the ID into the snippet.
                item
                    .getObject("snippet")
                    .put("id", item.getString("id"));

                try {
                    return Rson.DEFAULT.fromJson(item, YoutubeLiveBroadcastData.class);
                } catch (JsonParseException ex) {
                    throw new RuntimeException(ex);
                }
            })
            .collect(Collectors.toList());
    }

}
