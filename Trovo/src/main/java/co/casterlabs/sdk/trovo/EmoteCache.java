package co.casterlabs.sdk.trovo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.casterlabs.apiutil.web.WebRequest;
import lombok.NonNull;

public class EmoteCache {
    private static final String CHANNEL_EMOTE_FORMAT = "https://custom-file.trovo.live/file/custom_emote/%s/%s.png?imageView2/2/format/webp";
    private static final String GLOBAL_EMOTE_FORMAT = "https://img.trovo.live/emotes/%s.png?imageView2/2/format/webp";
    private static final Pattern EMOTE_PATTERN = Pattern.compile(":[a-z_]+");

    private Map<String, EmoteValidity> emotes = new HashMap<>();
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

            EmoteValidity cachedResult = this.emotes.get(name);

            if (cachedResult == null) {
                String formattedLink = this.formatLink(name);
                EmoteValidity validity = getValidity(formattedLink);

                if (validity == null) {
                    continue; // It's busted. We'll check again later.
                } else if (validity == EmoteValidity.VALID) {
                    this.emotes.put(name, EmoteValidity.VALID);
                    messageEmotes.put(emote, formattedLink);
                } else {
                    this.emotes.put(name, EmoteValidity.INVALID);
                }
            } else if (cachedResult == EmoteValidity.VALID) {
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

    private static enum EmoteValidity {
        VALID,
        INVALID;
    }

    private static EmoteValidity getValidity(@NonNull String address) {
        try {
            HttpResponse<Void> response = WebRequest.sendHttpRequest(HttpRequest.newBuilder(URI.create(address)), BodyHandlers.discarding(), null);
            if (response.statusCode() >= 200 && response.statusCode() <= 299) {
                return EmoteValidity.VALID;
            } else {
                return EmoteValidity.INVALID;
            }
        } catch (IOException e) {
            return null;
        }
    }

}
