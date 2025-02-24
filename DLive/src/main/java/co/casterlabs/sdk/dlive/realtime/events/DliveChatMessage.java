package co.casterlabs.sdk.dlive.realtime.events;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class DliveChatMessage {
    // https://dev.dlive.tv/schema/chattext.doc.html
    // Note that DLive internally calls emotes "emojis" and stickers "emotes"

    public final String id = null;
    public final DliveChatSender sender = null;
    public final String content = null;

    private final int[] emojis = null;

    public final @JsonExclude Long createdAt = null;

    @JsonDeserializationMethod("createdAt")
    private void $deserialize_createdAt(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        long value;
        if (e.isJsonString()) {
            value = Long.parseLong(e.getAsString());
        } else {
            value = e.getAsNumber().longValue();
        }

        Field f = DliveChatMessage.class.getField("createdAt");
        f.setAccessible(true);
        f.set(this, value);
    }

    public boolean isStickerMessage() {
        return this.content.matches(":(emote|emoji)\\/(global|vip|channel|mine)\\/[^\\/]+?\\/[a-z0-9]+_[0-9]{6}:");
    }

    public String getStickerUrl() {
        if (!this.isStickerMessage()) return null;

        // ":emote/global/lino/36df6c4070081e3_300300:"
        String stickerId = this.content.substring(
            this.content.lastIndexOf('/'),
            this.content.length() - 1
        );

        return String.format("https://images.prd.dlivecdn.com/emote/%s", stickerId);
    }

    public Map<String, String> getEmotes() {
        Map<String, String> emotes = new HashMap<>();

        for (int off = 0; off < this.emojis.length; off++) {
            // DLive encodes the emote positions as a pair of positions, in the same array.
            int start = this.emojis[off];
            int end = this.emojis[off + 1];

            String emoteName = this.content.substring(start, end);
            String emoteImageUrl = String.format("https://images.prd.dlivecdn.com/emoji/%s", emoteName);

            emotes.put(emoteName, emoteImageUrl);
        }

        return emotes;
    }

}
