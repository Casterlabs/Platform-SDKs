package co.casterlabs.sdk.youtube.requests;

import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import co.casterlabs.sdk.youtube.YoutubeApiRequest;
import co.casterlabs.sdk.youtube.YoutubeAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

public class YoutubeThumbnails {
    private static final String URL = "https://www.googleapis.com/upload/youtube/v3/thumbnails";

    @Setter
    @Accessors(chain = true, fluent = true)
    public static class Set extends YoutubeApiRequest<Void> {
        private String forVideoId;
        private byte[] withImageBytes;

        @SneakyThrows
        public Set(@NonNull YoutubeAuth auth) {
            super(auth);
        }

        @Override
        protected void validate() {
            assert !this.auth.isApplicationAuth() : "You must use user auth.";
            assert this.forVideoId != null : "You must specify a videoId.";
            assert this.withImageBytes != null : "You must include imageBytes.";
        }

        @Override
        protected String url() {
            return URL + "/set";
        }

        @Override
        protected @Nullable QueryBuilder query() {
            return QueryBuilder.from(
                "part", "id",
                "videoId", this.forVideoId
            );
        }

        @Override
        protected Builder request() {
            return super.request()
                .POST(BodyPublishers.ofByteArray(this.withImageBytes))
                .header("Content-Type", "application/octet-stream");
        }

        @Override
        protected Void deserialize(JsonObject json) throws JsonValidationException, JsonParseException {
            return null;
        }

    }

}
