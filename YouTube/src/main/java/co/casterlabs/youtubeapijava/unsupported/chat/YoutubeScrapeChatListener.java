package co.casterlabs.youtubeapijava.unsupported.chat;

import org.jetbrains.annotations.Nullable;

public interface YoutubeScrapeChatListener {

    public void onStart();

    public void onEnd(@Nullable String reason);

    public void onChat(YoutubeScrapeChatItem item);

    public void onError(String error);

}
