package co.casterlabs.sdk.youtube.requests;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import co.casterlabs.sdk.youtube.YoutubeApiRequest;
import co.casterlabs.sdk.youtube.YoutubeAuth;
import co.casterlabs.sdk.youtube.types.YoutubeChannelSnippet;
import lombok.NonNull;

public class YoutubeChannels {
    private static final String URL = "https://youtube.googleapis.com/youtube/v3/channels";

    public static class List extends YoutubeApiRequest.List<@Nullable YoutubeChannelSnippet> {
        private int queryMode = -1; // id, handle, mine
        private String queryData = null;

        public List(@NonNull YoutubeAuth auth) {
            super(auth);
        }

        public YoutubeChannels.List byId(@NonNull String channelId) {
            this.queryMode = 0;
            this.queryData = channelId;
            return this;
        }

        public YoutubeChannels.List byHandle(@NonNull String username) {
            if (username.startsWith("@")) {
                username = username.substring(1);
            }

            this.queryMode = 1;
            this.queryData = username;
            return this;
        }

        public YoutubeChannels.List mine() {
            this.queryMode = 2;
            this.queryData = null;
            return this;
        }

        @Override
        protected void validate() {
            assert this.queryMode != -1 : "You must specify a query either by ID or mine.";
            switch (this.queryMode) {
                case 0:
                case 1:
                    break;

                case 2:
                    assert !this.auth.isApplicationAuth() : "You must use user auth when requesting with mine().";
                    break;
            }
        }

        @Override
        protected String url() {
            return URL;
        }

        @Override
        protected @Nullable QueryBuilder query() {
            QueryBuilder query = QueryBuilder.from(
                "part", "snippet"
            );
            switch (this.queryMode) {
                case 0:
                    query.put("id", this.queryData);
                    break;

                case 1:
                    query.put("forUsername", this.queryData);
                    break;

                case 2:
                    query.put("mine", true);
                    break;
            }
            return query;
        }

        @Override
        protected @Nullable YoutubeChannelSnippet deserialize(JsonObject json) throws JsonValidationException, JsonParseException {
            if (!json.containsKey("items")) {
                return null;
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

}
