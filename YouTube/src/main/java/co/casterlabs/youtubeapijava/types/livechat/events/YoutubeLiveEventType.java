package co.casterlabs.youtubeapijava.types.livechat.events;

import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import co.casterlabs.youtubeapijava.YoutubeApi;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public enum YoutubeLiveEventType {
    // https://developers.google.com/youtube/v3/live/docs/liveChatMessages#resource
    CHAT_ENDED_EVENT(YoutubeLiveChatEndedEvent.class),
    MESSAGE_DELETED_EVENT(YoutubeLiveMessageDeletedEvent.class),
    SPONSOR_ONLY_MODE_ENDED_EVENT(YoutubeLiveSponsorOnlyModeEndedEvent.class),
    SPONSOR_ONLY_MODE_STARTED_EVENT(YoutubeLiveSponsorOnlyModeStartedEvent.class),
    NEW_SPONSOR_EVENT(YoutubeLiveNewSponsorEvent.class),
    MEMBER_MILESTONE_CHAT_EVENT(YoutubeLiveMemberMilestoneChatEvent.class),
    SUPER_CHAT_EVENT(YoutubeLiveSuperChatEvent.class),
    SUPER_STICKER_EVENT(YoutubeLiveSuperStickerEvent.class),
    TEXT_MESSAGE_EVENT(YoutubeLiveTextMessageEvent.class),
    TOMBSTONE(YoutubeLiveTombstoneEvent.class),
    USER_BANNED_EVENT(YoutubeLiveUserBannedEvent.class),
    MEMBERSHIP_GIFTING_EVENT(YoutubeLiveMembershipGiftingEvent.class),
    GIFT_MEMBERSHIP_RECEIVED_EVENT(YoutubeLiveChatEndedEvent.class),
    ;

    private Class<? extends YoutubeLiveEvent> clazz;

    @SuppressWarnings({
            "unchecked",
            "deprecation"
    })
    public static <T extends YoutubeLiveEvent> T fromJson(@NonNull JsonObject json) throws JsonValidationException, JsonParseException {
        // Remove all the subtypes and extract them to the root snippet.
        final String[] subs = {};

        for (String sub : subs) {
            if (json.containsKey(sub)) {
                json.getMap().putAll(json.remove(sub).getAsObject().getMap());
            }
        }

        // Grab the class, deserialize it, and return it.
        YoutubeLiveEventType type = YoutubeLiveEventType.parseType(json.get("type").getAsString());

        return (T) YoutubeApi.RSON.fromJson(json, type.clazz);
    }

    public static YoutubeLiveEventType parseType(@NonNull String type) {
        String converted = type
            .replaceAll("([A-Z])", "_$1") // textMessageEvent -> text_Message_Event
            .toUpperCase(); // text_Message_Event -> TEXT_MESSAGE_EVENT

        return YoutubeLiveEventType.valueOf(converted);
    }

}
