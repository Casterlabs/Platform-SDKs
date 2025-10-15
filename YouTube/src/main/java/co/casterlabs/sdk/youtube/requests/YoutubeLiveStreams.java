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
import co.casterlabs.sdk.youtube.types.YoutubeLiveStreamCdn;
import co.casterlabs.sdk.youtube.types.YoutubeLiveStreamSnippet;
import lombok.NonNull;
import lombok.ToString;

public class YoutubeLiveStreams {
    private static final String URL = "https://www.googleapis.com/youtube/v3/liveStreams";

    public static class List extends YoutubeApiRequest.List<java.util.List<YoutubeLiveStreamData>> {
        private int queryMode = -1; // id, mine
        private String queryData = null;

        public List(@NonNull YoutubeAuth auth) {
            super(auth);
        }

        public YoutubeLiveStreams.List byId(@NonNull String streamId) {
            this.queryMode = 0;
            this.queryData = streamId;
            return this;
        }

        public YoutubeLiveStreams.List mine() {
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
                "part", "id,snippet,cdn,status",
                "maxResults", "50"
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
        protected java.util.List<YoutubeLiveStreamData> deserialize(JsonObject json) throws JsonValidationException, JsonParseException {
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
                    try {
                        return Rson.DEFAULT.fromJson(item, YoutubeLiveStreamData.class);
                    } catch (JsonParseException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.toList());
        }
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class YoutubeLiveStreamData {
        public final String id = null;
        public final YoutubeLiveStreamSnippet snippet = null;
        public final YoutubeLiveStreamCdn cdn = null;

    }

}
