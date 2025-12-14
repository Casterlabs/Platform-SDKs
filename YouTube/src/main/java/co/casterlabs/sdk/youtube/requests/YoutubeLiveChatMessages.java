package co.casterlabs.sdk.youtube.requests;

import java.io.Closeable;
import java.util.Iterator;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import co.casterlabs.sdk.youtube.YoutubeApiRequest;
import co.casterlabs.sdk.youtube.YoutubeAuth;
import co.casterlabs.sdk.youtube.types.YoutubeLiveChatMessagesList;
import co.casterlabs.sdk.youtube.types.protobuf.LiveChatMessage;
import co.casterlabs.sdk.youtube.types.protobuf.LiveChatMessageListRequest;
import co.casterlabs.sdk.youtube.types.protobuf.LiveChatMessageListResponse;
import co.casterlabs.sdk.youtube.types.protobuf.V3DataLiveChatMessageServiceGrpc;
import co.casterlabs.sdk.youtube.types.protobuf.V3DataLiveChatMessageServiceGrpc.V3DataLiveChatMessageServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
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
//                "maxResults", "2000",
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

    public static class StreamList implements Closeable {
        private static final Metadata.Key<String> API_KEY_METADATA = Metadata.Key.of("x-goog-api-key", Metadata.ASCII_STRING_MARSHALLER);
        private static final Metadata.Key<String> AUTH_METADATA = Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);

        private final Metadata authMetadata;
        private final LiveChatMessageListRequest.Builder request;
        private final Listener listener;

        private boolean shouldRun = false;

        public StreamList(@NonNull YoutubeAuth auth, @NonNull String liveChatId, @NonNull Listener listener) throws ApiAuthException {
            this.authMetadata = new Metadata();
            if (auth.isApplicationAuth()) {
                this.authMetadata.put(API_KEY_METADATA, auth.getApiKey());
            } else {
                this.authMetadata.put(AUTH_METADATA, "Bearer " + auth.getAccessToken());
            }

            this.listener = listener;
            this.request = LiveChatMessageListRequest.newBuilder()
                .addPart("id")
                .addPart("snippet")
                .addPart("authorDetails")
                .setLiveChatId(liveChatId)
                .setMaxResults(500);
        }

        public void run() throws ApiAuthException {
            if (this.shouldRun) {
                throw new IllegalStateException("Stream is already running");
            }

            ManagedChannel channel = ManagedChannelBuilder.forTarget("dns:///youtube.googleapis.com:443")
                .useTransportSecurity()
                .intercept(MetadataUtils.newAttachHeadersInterceptor(this.authMetadata))
                .build();

            try {
                this.shouldRun = true;
                this.listener.onOpen();

                V3DataLiveChatMessageServiceBlockingStub stub = V3DataLiveChatMessageServiceGrpc.newBlockingStub(channel);

                // TODO figure out _why_ youtube kills the stream after exactly 10 seconds.
                while (this.shouldRun) {
                    Iterator<LiveChatMessageListResponse> it = stub.streamList(this.request.build());
                    while (it.hasNext()) {
                        LiveChatMessageListResponse resp = it.next();
                        this.request.setPageToken(resp.getNextPageToken());

                        for (LiveChatMessage message : resp.getItemsList()) {
                            this.listener.onEvent(message);
                        }
                    }
                }
            } catch (Throwable t) {
                this.listener.onError(t);
            } finally {
                channel.shutdownNow();
                this.listener.onClose();
                this.shouldRun = false;
            }
        }

        public boolean isRunning() {
            return this.shouldRun;
        }

        @Override
        public void close() {
            this.shouldRun = false;
        }

        public static interface Listener {

            default void onOpen() {}

            default void onClose() {}

            default void onError(Throwable t) {}

            default void onEvent(LiveChatMessage message) {}

        }

    }

}
