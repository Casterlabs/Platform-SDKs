package co.casterlabs.sdk.youtube.requests;

import java.util.Collections;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import co.casterlabs.sdk.youtube.YoutubeApiRequest;
import co.casterlabs.sdk.youtube.YoutubeAuth;
import co.casterlabs.sdk.youtube.types.YoutubeLiveBroadcastSnippet;
import co.casterlabs.sdk.youtube.types.YoutubeLiveBroadcastStatus;
import lombok.NonNull;
import lombok.ToString;

public class YoutubeLiveBroadcasts {
    private static final String URL = "https://youtube.googleapis.com/youtube/v3/liveBroadcasts";

    public static class List extends YoutubeApiRequest.List<java.util.List<YoutubeLiveBroadcastData>> {
        private int queryMode = -1; // id, mine
        private String queryData = null;

        public List(@NonNull YoutubeAuth auth) {
            super(auth);
        }

        public YoutubeLiveBroadcasts.List byId(@NonNull String videoId) {
            this.queryMode = 0;
            this.queryData = videoId;
            return this;
        }

        public YoutubeLiveBroadcasts.List mine() {
            this.queryMode = 1;
            this.queryData = null;
            return this;
        }

        @Override
        protected void validate() {
            assert this.queryMode != -1 : "You must specify a query either by ID or mine().";
            switch (this.queryMode) {
                case 0:
                    break;

                case 1:
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
                "part", "snippet,status"
            );
            switch (this.queryMode) {
                case 0:
                    query.put("id", this.queryData);
                    break;
                case 1:
                    query.put("mine", true);
                    break;
            }
            return query;
        }

        @Override
        protected java.util.List<YoutubeLiveBroadcastData> deserialize(JsonObject json) throws JsonValidationException, JsonParseException {
            if (!json.containsKey("items")) {
                return Collections.emptyList();
            }

            JsonArray items = json.getArray("items");
            if (items.isEmpty()) {
                return Collections.emptyList();
            }

            return items.toList()
                .stream()
                .map((e) -> e.getAsObject())
                .map((item) -> {
                    // Inject the ID into the snippet.
                    item
                        .getObject("snippet")
                        .put("id", item.getString("id"));
                    return item;
                })
                .map((item) -> {
                    try {
                        return Rson.DEFAULT.fromJson(item, YoutubeLiveBroadcastData.class);
                    } catch (JsonParseException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.toList());
        }
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class YoutubeLiveBroadcastData {
        public final String id = null;
        public final YoutubeLiveBroadcastSnippet snippet = null;
        public final YoutubeLiveBroadcastStatus status = null;

        public String videoUrl() {
            return String.format("https://youtu.be/%s", this.id);
        }

    }

}
