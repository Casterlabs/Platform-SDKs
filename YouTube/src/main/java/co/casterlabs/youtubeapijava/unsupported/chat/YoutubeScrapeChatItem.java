package co.casterlabs.youtubeapijava.unsupported.chat;

import java.time.Instant;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;

@Getter
@JsonClass(exposeAll = true)
public class YoutubeScrapeChatItem {
    private ScrapeChatAuthor author;
    private ScrapeMessageItem[] message;
    private @Nullable ScrapeSuperChat superchat;
    private boolean isMembership;
    private boolean isVerified;
    private boolean isOwner;
    private boolean isModerator;
    private boolean isHistorical;
    private Instant timestamp;

    @Getter
    @JsonClass(exposeAll = true)
    public static class ScrapeMessageItem {
        // Text
        private @Nullable String text;

        // Emoji
        private String url;
        private String alt;
        private @Nullable String emojiText;
        private @Nullable boolean isCustomEmoij;

        // -
        public boolean isTextItem() {
            return this.text != null;
        }

    }

    @Getter
    @JsonClass(exposeAll = true)
    public static class ScrapeSuperChat {
        private String amount;
        private String color;
        private @Nullable ScrapeImageItem sticker;

    }

    @Getter
    @JsonClass(exposeAll = true)
    public static class ScrapeChatAuthor {
        private String name;
        private @Nullable ScrapeImageItem thumbnail;
        private String channelId;
        private @Nullable ScrapeBadge badge;
    }

    @Getter
    @JsonClass(exposeAll = true)
    public static class ScrapeBadge {
        private ScrapeImageItem thumbnail;
        private String label;
    }

    @Getter
    @JsonClass(exposeAll = true)
    public static class ScrapeImageItem {
        private String url;
        private String alt;
    }

}
