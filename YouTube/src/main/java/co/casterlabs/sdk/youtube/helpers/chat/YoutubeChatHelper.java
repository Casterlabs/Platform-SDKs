package co.casterlabs.sdk.youtube.helpers.chat;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.sdk.youtube.YoutubeAuth;
import co.casterlabs.sdk.youtube.requests.YoutubeLiveChatMessages;
import co.casterlabs.sdk.youtube.types.YoutubeLiveChatMessagesList;
import co.casterlabs.sdk.youtube.types.livechat.YoutubeLiveChatEvent;
import co.casterlabs.sdk.youtube.types.livechat.events.YoutubeLiveEventType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class YoutubeChatHelper {
    private @Getter String liveChatId;
    private YoutubeAuth auth;
    private YoutubeChatListener listener;

    private Thread thread;
    private boolean shouldLoop = true;
    private String paginationToken = null;

    private @Setter long overridePollingInterval = -1; // -1 to use the recommended value YouTube provides.

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
            YoutubeLiveChatMessagesList list = new YoutubeLiveChatMessages.List(this.auth)
                .forLiveChatId(this.liveChatId)
                .withPageToken(this.paginationToken)
                .send();

            if (list.isHistorical) {
                this.listener.onHistory(list.events);
            } else {
                for (YoutubeLiveChatEvent lce : list.events) {
                    try {
                        this.listener.onEvent(lce);
                    } catch (Throwable t) {
                        this.listener.onError(t);
                    }

                    if (lce.event().type == YoutubeLiveEventType.CHAT_ENDED_EVENT) {
                        // We're done. Cleanup!
                        this.shouldLoop = false;
                    }
                }
            }

            this.paginationToken = list.nextPageToken;

            Thread.sleep(this.overridePollingInterval == -1 ? list.pollingIntervalMillis : this.overridePollingInterval);
        } catch (ApiException e) {
            String message = e.getMessage();
            if (message.contains("The live chat that you are trying to retrieve cannot be found.") ||
                message.contains("The live chat is no longer live.")) {
                this.shouldLoop = false;
                this.listener.onClose();
            } else {
                this.listener.onError(e);
            }
        } catch (Throwable t) {
            this.listener.onError(t);
        }
    }

    public boolean isRunning() {
        return this.shouldLoop;
    }

    public void stop() {
        this.shouldLoop = false;
    }

}
