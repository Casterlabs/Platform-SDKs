package co.casterlabs.sdk.youtube.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import co.casterlabs.sdk.youtube.YoutubeApiRequest;
import co.casterlabs.sdk.youtube.YoutubeAuth;
import co.casterlabs.sdk.youtube.types.YoutubeLiveBroadcastContentDetails;
import co.casterlabs.sdk.youtube.types.YoutubeLiveBroadcastSnippet;
import co.casterlabs.sdk.youtube.types.YoutubeLiveBroadcastStatus;
import co.casterlabs.sdk.youtube.types.YoutubeLiveBroadcastStatus.PrivacyStatus;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.experimental.Accessors;

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
                "part", "id,snippet,contentDetails,status",
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
                    try {
                        return Rson.DEFAULT.fromJson(item, YoutubeLiveBroadcastData.class);
                    } catch (JsonParseException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.toList());
        }
    }

    @Setter
    @Accessors(chain = true, fluent = true)
    public static class Insert extends YoutubeApiRequest.Insert<YoutubeLiveBroadcastData> {
        private String withTitle = null;
        private PrivacyStatus withPrivacyStatus = null;

        private @Setter(AccessLevel.NONE) String startTime = null;

        public Insert(@NonNull YoutubeAuth auth) {
            super(auth);
        }

        public YoutubeLiveBroadcasts.Insert withStartTime(long time) {
            // ISO 8601 format
            ZonedDateTime zdt = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(time),
                ZoneId.systemDefault()
            );

            this.startTime = zdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            return this;
        }

        @Override
        protected void validate() {
            assert !this.auth.isApplicationAuth() : "You must use user auth.";
            assert this.withTitle != null : "You must specify a title.";
            assert this.withPrivacyStatus != null : "You must specify a privacyStatus.";
            assert this.startTime != null : "You must specify a startTime.";
        }

        @Override
        protected String url() {
            return URL;
        }

        @Override
        protected @Nullable QueryBuilder query() {
            return QueryBuilder.from(
                "part", "id,snippet,contentDetails,status"
            );
        }

        @Override
        protected String body() {
            return new JsonObject()
                .put(
                    "snippet",
                    new JsonObject()
                        .put("title", this.withTitle)
                        .put("scheduledStartTime", this.startTime)
                )
                .put(
                    "status",
                    new JsonObject()
                        .put("privacyStatus", this.withPrivacyStatus.name().toLowerCase())
                )
                .toString();
        }

        @Override
        protected YoutubeLiveBroadcastData deserialize(JsonObject json) throws JsonValidationException, JsonParseException {
            try {
                return Rson.DEFAULT.fromJson(json, YoutubeLiveBroadcastData.class);
            } catch (JsonParseException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public static class Update extends YoutubeApiRequest.Update<YoutubeLiveBroadcastData> {
        private JsonObject body;

        @SneakyThrows
        public Update(@NonNull YoutubeAuth auth) {
            super(auth);

            this.body = new JsonObject()
                // Initialize the structure.
                .put("snippet", new JsonObject())
                .put("status", new JsonObject())
                .put("contentDetails", new JsonObject().put("monitorStream", new JsonObject()))
                .put("monetizationDetails", new JsonObject().put("cuepointSchedule", new JsonObject()));
        }

        public YoutubeLiveBroadcasts.Update forId(@NonNull String id) {
            this.body.put("id", id);
            return this;
        }

        /**
         * Helper to prefill the update request with data from the existing live
         * broadcast.
         */
        public YoutubeLiveBroadcasts.Update prefill() throws IOException, ApiException {
            QueryBuilder query = QueryBuilder.from(
                "part", "id,snippet,contentDetails,status",
                "id", this.body.getString("id")
            );

            HttpRequest.Builder request = HttpRequest
                .newBuilder()
                .uri(URI.create(URL + "?" + query))
                .header("X-Client-Type", "api");

            HttpResponse<JsonObject> response = WebRequest.sendHttpRequest(
                request,
                RsonBodyHandler.of(JsonObject.class),
                this.auth
            );
            JsonObject body = response.body();

            if (response.statusCode() >= 200 && response.statusCode() <= 299) {
                // Success!
                this.body = body.getArray("items").getObject(0);
                return this;
            }

            if (response.statusCode() == 401) {
                throw new ApiAuthException(body.toString());
            } else {
                throw new ApiException(body.toString());
            }
        }

        public YoutubeLiveBroadcasts.Update withSnippetTitle(String title) {
            this.body.getObject("snippet").put("title", title);
            return this;
        }

        public YoutubeLiveBroadcasts.Update withSnippetDescription(String description) {
            this.body.getObject("snippet").put("description", description);
            return this;
        }

        public YoutubeLiveBroadcasts.Update withSnippetScheduledStartTime(long time) {
            // ISO 8601 format
            ZonedDateTime zdt = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(time),
                ZoneId.systemDefault()
            );

            String formatted = zdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            this.body.getObject("snippet").put("scheduledStartTime", formatted);
            return this;
        }

        public YoutubeLiveBroadcasts.Update withSnippetScheduledEndTime(long time) {
            // ISO 8601 format
            ZonedDateTime zdt = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(time),
                ZoneId.systemDefault()
            );

            String formatted = zdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            this.body.getObject("snippet").put("scheduledEndTime", formatted);
            return this;
        }

        public YoutubeLiveBroadcasts.Update withStatusPrivacyStatus(@NonNull PrivacyStatus privacyStatus) {
            this.body.getObject("status").put("privacyStatus", privacyStatus.name().toLowerCase());
            return this;
        }

        public YoutubeLiveBroadcasts.Update withContentDetailsMonitorStreamEnableMonitorStream(boolean enable) {
            this.body.getObject("contentDetails").getObject("monitorStream").put("enableMonitorStream", enable);
            return this;
        }

        public YoutubeLiveBroadcasts.Update withContentDetailsMonitorStreamBroadcastStreamDelayMs(long broadcastStreamDelayMs) {
            this.body.getObject("contentDetails").getObject("monitorStream").put("broadcastStreamDelayMs", broadcastStreamDelayMs);
            return this;
        }

        public YoutubeLiveBroadcasts.Update withContentDetailsEnableAutoStart(boolean enable) {
            this.body.getObject("contentDetails").put("enableAutoStart", enable);
            return this;
        }

        public YoutubeLiveBroadcasts.Update withContentDetailsEnableAutoStop(boolean enable) {
            this.body.getObject("contentDetails").put("enableAutoStop", enable);
            return this;
        }

        public YoutubeLiveBroadcasts.Update withContentDetailsEnableClosedCaptions(boolean enable) {
            this.body.getObject("contentDetails").put("enableClosedCaptions", enable);
            return this;
        }

        public YoutubeLiveBroadcasts.Update withContentDetailsEnableDvr(boolean enable) {
            this.body.getObject("contentDetails").put("enableDvr", enable);
            return this;
        }

        public YoutubeLiveBroadcasts.Update withContentDetailsEnableEmbed(boolean enable) {
            this.body.getObject("contentDetails").put("enableEmbed", enable);
            return this;
        }

        public YoutubeLiveBroadcasts.Update withContentDetailsRecordFromStart(boolean enable) {
            this.body.getObject("contentDetails").put("recordFromStart", enable);
            return this;
        }

        public YoutubeLiveBroadcasts.Update withMonetizationDetailsCuepointSchedulePauseAdsUntil(long time) {
            // ISO 8601 format
            ZonedDateTime zdt = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(time),
                ZoneId.systemDefault()
            );

            String formatted = zdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            this.body.getObject("monetizationDetails").getObject("cuepointSchedule").put("pauseAdsUntil", formatted);
            return this;
        }

        @Override
        protected void validate() {
            assert !this.auth.isApplicationAuth() : "You must use user auth.";
        }

        @Override
        protected String url() {
            return URL;
        }

        @Override
        protected @Nullable QueryBuilder query() {
            return QueryBuilder.from(
                "part", "id,snippet,contentDetails,status"
            );
        }

        @Override
        protected String body() {
            return this.body.toString();
        }

        @Override
        protected YoutubeLiveBroadcastData deserialize(JsonObject json) throws JsonValidationException, JsonParseException {
            try {
                return Rson.DEFAULT.fromJson(json, YoutubeLiveBroadcastData.class);
            } catch (JsonParseException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public static class Bind extends YoutubeApiRequest.Bind<Void> {
        private String broadcastId;
        private String streamId = null;

        @SneakyThrows
        public Bind(@NonNull YoutubeAuth auth) {
            super(auth);
        }

        public YoutubeLiveBroadcasts.Bind forBroadcastId(@NonNull String broadcastId) {
            this.broadcastId = broadcastId;
            return this;
        }

        /**
         * @param streamId A null value is equivalent to calling {@link #noStreamId()}
         */
        public YoutubeLiveBroadcasts.Bind toStreamId(@Nullable String streamId) {
            this.streamId = streamId;
            return this;
        }

        public YoutubeLiveBroadcasts.Bind noStreamId() {
            this.streamId = null;
            return this;
        }

        @Override
        protected void validate() {
            assert !this.auth.isApplicationAuth() : "You must use user auth.";
        }

        @Override
        protected String url() {
            return URL + "/bind";
        }

        @Override
        protected @Nullable QueryBuilder query() {
            return QueryBuilder.from(
                "part", "id",
                "id", this.broadcastId
            ).optionalPut("streamId", this.streamId);
        }

        @Override
        protected String body() {
            return null;
        }

        @Override
        protected Void deserialize(JsonObject json) throws JsonValidationException, JsonParseException {
            return null;
        }

    }

    @Setter
    @Accessors(chain = true, fluent = true)
    public static class Delete extends YoutubeApiRequest.Delete<Void> {
        private String forId;

        @SneakyThrows
        public Delete(@NonNull YoutubeAuth auth) {
            super(auth);
        }

        @Override
        protected void validate() {
            assert !this.auth.isApplicationAuth() : "You must use user auth.";
            assert this.forId != null : "You must specify an id.";
        }

        @Override
        protected String url() {
            return URL;
        }

        @Override
        protected @Nullable QueryBuilder query() {
            return QueryBuilder.from(
                "id", this.forId
            );
        }

        @Override
        protected Void deserialize(JsonObject json) throws JsonValidationException, JsonParseException {
            return null;
        }

    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class YoutubeLiveBroadcastData {
        public final String id = null;
        public final YoutubeLiveBroadcastSnippet snippet = null;
        public final YoutubeLiveBroadcastStatus status = null;
        public final YoutubeLiveBroadcastContentDetails contentDetails = null;

        public String videoUrl() {
            return String.format("https://youtu.be/%s", this.id);
        }

    }

}
