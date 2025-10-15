package co.casterlabs.sdk.youtube.requests;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import co.casterlabs.sdk.youtube.YoutubeApiRequest;
import co.casterlabs.sdk.youtube.YoutubeAuth;
import co.casterlabs.sdk.youtube.types.YoutubeLiveChatMessagesList;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

public class YoutubeLiveChatMessages {
    private static final String URL = "https://youtube.googleapis.com/youtube/v3/liveChat/messages";

    @Setter
    @Accessors(chain = true, fluent = true)
    public static class Delete extends YoutubeApiRequest.Delete<Void> {
        private @Nullable String byMessageId = null;

        public Delete(@NonNull YoutubeAuth auth) {
            super(auth);
        }

        @Override
        protected void validate() {
            assert this.byMessageId != null : "You must specify a message id.";
            assert !this.auth.isApplicationAuth() : "You must use user auth when deleting a chat message.";
        }

        @Override
        protected String url() {
            return URL;
        }

        @Override
        protected @Nullable QueryBuilder query() {
            return QueryBuilder.from(
                "id", this.byMessageId
            );
        }

        @Override
        protected Void deserialize(JsonObject json) {
            return null;
        }
    }

    @Setter
    @Accessors(chain = true, fluent = true)
    public static class Insert extends YoutubeApiRequest.Insert<Void> {
        private @NonNull String forLiveChatId = null;
        private @Nullable String withMessageText = null;

        public Insert(@NonNull YoutubeAuth auth) {
            super(auth);
        }

        @Override
        protected void validate() {
            assert this.forLiveChatId != null : "You must specify a chat id.";
            assert this.withMessageText != null : "You must specify message text.";
            assert !this.auth.isApplicationAuth() : "You must use user auth when inserting a chat message.";
        }

        @Override
        protected String url() {
            return URL;
        }

        @Override
        protected @Nullable QueryBuilder query() {
            return QueryBuilder.from(
                "part", "snippet"
            );
        }

        @Override
        protected String body() {
            return new JsonObject()
                .put(
                    "snippet",
                    new JsonObject()
                        .put("liveChatId", this.forLiveChatId)
                        .put("type", "textMessageEvent")
                        .put(
                            "textMessageDetails",
                            new JsonObject()
                                .put("messageText", this.withMessageText)
                        )
                )
                .toString();
        }

        @Override
        protected Void deserialize(JsonObject json) {
            return null;
        }
    }

    @Setter
    @Accessors(chain = true, fluent = true)
    public static class List extends YoutubeApiRequest.List<YoutubeLiveChatMessagesList> {
        private @NonNull String forLiveChatId = null;
        private @Nullable String withPageToken = null;

        public List(@NonNull YoutubeAuth auth) {
            super(auth);
        }

        @Override
        protected void validate() {
            assert this.forLiveChatId != null : "You must specify a chat id.";
        }

        @Override
        protected String url() {
            return URL;
        }

        @Override
        protected @Nullable QueryBuilder query() {
            return QueryBuilder.from(
                "part", "snippet,authorDetails",
                "profileImageSize", "88",
//	              "maxResults", "2000",
                "liveChatId", this.forLiveChatId,
                "pageToken", this.withPageToken // optional
            );
        }

        @Override
        protected YoutubeLiveChatMessagesList deserialize(JsonObject json) throws JsonValidationException, JsonParseException {
            json.put("isHistorical", this.withPageToken == null);
            return Rson.DEFAULT.fromJson(json, YoutubeLiveChatMessagesList.class);
        }
    }

}
