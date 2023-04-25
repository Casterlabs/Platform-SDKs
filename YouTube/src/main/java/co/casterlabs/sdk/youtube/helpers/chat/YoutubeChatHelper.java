package co.casterlabs.sdk.youtube.helpers.chat;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.sdk.youtube.YoutubeAuth;
import co.casterlabs.sdk.youtube.requests.YoutubeListLiveChatMessagesRequest;
import co.casterlabs.sdk.youtube.types.YoutubeLiveChatMessagesList;
import co.casterlabs.sdk.youtube.types.livechat.YoutubeLiveChatEvent;
import lombok.Getter;
import lombok.NonNull;

public class YoutubeChatHelper {
    private @Getter String liveChatId;
    private YoutubeAuth auth;
    private YoutubeChatListener listener;

    private Thread thread;
    private boolean shouldLoop = true;
    private String paginationToken = null;

    public YoutubeChatHelper(@NonNull String liveChatId, @NonNull YoutubeAuth auth, @NonNull YoutubeChatListener listener) {
        this.liveChatId = liveChatId;
        this.auth = auth;
        this.listener = listener;

        this.thread = new Thread(() -> {
            while (this.shouldLoop) {
                this.doLoop();
            }
            this.shouldLoop = false;
        });
        this.thread.setName("YoutubeLiveChat: " + this.liveChatId);
        this.thread.start();
    }

    private void doLoop() {
        try {
            YoutubeLiveChatMessagesList list = new YoutubeListLiveChatMessagesRequest(this.auth)
                .setLiveChatId(this.liveChatId)
                .setPageToken(this.paginationToken)
                .send();

            if (list.isHistorical()) {
                this.listener.onHistory(list.getEvents());
            } else {
                // New thread for the easing code, since messages are polled.
                new Thread(() -> {
                    try {
                        long referencePoint = Long.MAX_VALUE;

                        for (YoutubeLiveChatEvent event : list.getEvents()) {
                            // Get the timestamp of the event, compare it to the last message timestamp,
                            // then wait for the difference to help ease the sudden influx of messages.
                            // This atmost delays messages by 2s and is normally ~1s.
                            long publishedAt = event.getEvent().getPublishedAt().toEpochMilli();
                            long timeBetweenPollAndPublish = publishedAt - referencePoint;
                            referencePoint = publishedAt;

                            if (timeBetweenPollAndPublish > 0) {
                                Thread.sleep(timeBetweenPollAndPublish);
                            }

                            this.listener.onEvent(event);
                        }
                    } catch (Throwable t) {
                        this.shouldLoop = false;
                        listener.onError(t);
                    }
                }).start();
            }

            this.paginationToken = list.getNextPageToken();
            Thread.sleep(list.getPollingIntervalMillis());
        } catch (ApiException e) {
            this.shouldLoop = false;

            String message = e.getMessage();

            if (message.contains("The live chat that you are trying to retrieve cannot be found.") ||
                message.contains("The live chat is no longer live.")) {
                listener.onClose();
            } else {
                listener.onError(e);
            }
        } catch (Throwable t) {
            this.shouldLoop = false;
            listener.onError(t);
        }
    }

    public boolean isRunning() {
        return this.shouldLoop;
    }

    public void stop() {
        this.shouldLoop = false;
    }

}
