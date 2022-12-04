package co.casterlabs.trovoapi;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.NonNull;

public class EmoteCache {
    private static final String CHANNEL_EMOTE_FORMAT = "https://custom-file.trovo.live/file/custom_emote/%s/%s.png?imageView2/2/format/webp";
    private static final String GLOBAL_EMOTE_FORMAT = "https://img.trovo.live/emotes/%s.png?imageView2/2/format/webp";
    private static final Pattern EMOTE_PATTERN = Pattern.compile(":[a-z_]+");

    private Map<String, EmoteResult> emotes = new HashMap<>();
    private String channelId;

    public EmoteCache() {}

    public EmoteCache(@NonNull String channelId) {
        this.channelId = channelId;
    }

    public boolean isChannelMode() {
        return this.channelId != null;
    }

    public Map<String, String> parseEmotes(@NonNull String message) {
        Map<String, String> messageEmotes = new HashMap<>();

        Matcher m = EMOTE_PATTERN.matcher(message);
        while (m.find()) {
            String emote = m.group();
            String name = emote.substring(1);

            EmoteResult cachedResult = this.emotes.get(name);

            if (cachedResult == null) {
                String formattedLink = this.formatLink(name);

                if (HttpUtil.httpExists(formattedLink)) {
                    this.emotes.put(name, EmoteResult.VALID);
                    messageEmotes.put(emote, formattedLink);
                } else {
                    this.emotes.put(name, EmoteResult.INVALID);
                }
            } else if (cachedResult == EmoteResult.VALID) {
                messageEmotes.put(emote, this.formatLink(name));
            }
        }

        return messageEmotes;
    }

    public void clear() {
        this.emotes.clear();
    }

    private String formatLink(String name) {
        if (this.isChannelMode()) {
            return String.format(CHANNEL_EMOTE_FORMAT, this.channelId, name);
        } else {
            return String.format(GLOBAL_EMOTE_FORMAT, name);
        }
    }

    private static enum EmoteResult {
        VALID,
        INVALID;

    }

}
