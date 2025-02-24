package co.casterlabs.sdk.youtube.requests;

import java.io.IOException;

import org.unbescape.uri.UriEscape;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.youtube.YoutubeAuth;
import co.casterlabs.sdk.youtube.YoutubeHttpUtil;
import co.casterlabs.sdk.youtube.types.YoutubeChannelSnippet;
import lombok.NonNull;

public class YoutubeGetChannelSnippetRequest extends AuthenticatedWebRequest<YoutubeChannelSnippet, YoutubeAuth> {
    private int queryMode = -1; // id, handle, mine
    private String queryData = null;

    public YoutubeGetChannelSnippetRequest(@NonNull YoutubeAuth auth) {
        super(auth);
    }

    public YoutubeGetChannelSnippetRequest byId(@NonNull String channelId) {
        this.queryMode = 0;
        this.queryData = channelId;
        return this;
    }

    public YoutubeGetChannelSnippetRequest byHandle(@NonNull String username) {
        if (username.startsWith("@")) {
            username = username.substring(1);
        }

        this.queryMode = 1;
        this.queryData = username;
        return this;
    }

    public YoutubeGetChannelSnippetRequest mine() {
        this.queryMode = 2;
        this.queryData = null;
        return this;
    }

    @Override
    protected YoutubeChannelSnippet execute() throws ApiException, ApiAuthException, IOException {
        assert this.queryMode != -1 : "You must specify a query either by ID or mine.";

        String url = "https://youtube.googleapis.com/youtube/v3/channels"
            + "?part=snippet";

        switch (this.queryMode) {
            case 0: {
                url += String.format("&id=%s", UriEscape.escapeUriQueryParam(this.queryData));
                break;
            }

            case 1: {
                url += String.format("&forUsername=%s", UriEscape.escapeUriQueryParam(this.queryData));
                break;
            }

            case 2: {
                if (this.auth.isApplicationAuth()) {
                    throw new ApiAuthException("You must use user auth when requesting `mine()`");
                }

                url += "&mine=true";
                break;
            }
        }

        JsonObject json = YoutubeHttpUtil.list(url, this.auth);
        if (!json.containsKey("items")) {
            throw new ApiException("Not found.");
        }

        JsonArray items = json.getArray("items");

        if (items.isEmpty()) {
            return null;
        }

        JsonObject item = items.getObject(0);

        JsonObject snippet = item.getObject("snippet");
        snippet.put("id", item.get("id"));

        return Rson.DEFAULT.fromJson(snippet, YoutubeChannelSnippet.class);
    }

}
