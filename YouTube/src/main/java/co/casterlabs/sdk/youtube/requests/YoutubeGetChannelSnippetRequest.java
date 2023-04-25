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
import co.casterlabs.sdk.youtube.types.YoutubeChannelSnippet;
import lombok.NonNull;
import okhttp3.Response;

public class YoutubeGetChannelSnippetRequest extends AuthenticatedWebRequest<YoutubeChannelSnippet, YoutubeAuth> {
    private int queryMode = -1; // id, username, mine
    private String queryData = null;

    public YoutubeGetChannelSnippetRequest(@NonNull YoutubeAuth auth) {
        super(auth);
    }

    public YoutubeGetChannelSnippetRequest byId(@NonNull String channelId) {
        this.queryMode = 0;
        this.queryData = channelId;
        return this;
    }

    public YoutubeGetChannelSnippetRequest byUsername(@NonNull String username) {
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
                url += String.format("&id=%s", HttpUtil.encodeURIComponent(this.queryData));
                break;
            }

            case 1: {
                url += String.format("&forUsername=%s", HttpUtil.encodeURIComponent(this.queryData));
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
                snippet.put("id", item.get("id"));

                return YoutubeApi.RSON.fromJson(snippet, YoutubeChannelSnippet.class);
            } else if (response.code() == 401) {
                throw new ApiAuthException(body);
            } else {
                throw new ApiException(body);
            }
        } catch (JsonParseException e) {
            throw new ApiException(e);
        }
    }

}
