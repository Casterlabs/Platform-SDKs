package co.casterlabs.sdk.youtube.helpers.chat;

import java.util.List;

import co.casterlabs.sdk.youtube.types.livechat.YoutubeLiveChatEvent;

public interface YoutubeChatListener {

    default void onOpen() {}

    default void onClose() {}

    default void onError(Throwable t) {}

    default void onHistory(List<YoutubeLiveChatEvent> events) {}

    default void onEvent(YoutubeLiveChatEvent event) {}

}
