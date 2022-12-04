package co.casterlabs.sdk.caffeine.realtime.messages;

public interface CaffeineMessagesListener {

    default void onOpen() {}

    public void onChat(ChatEvent event);

    public void onShare(ShareEvent event);

    public void onProp(PropEvent event);

    public void onUpvote(UpvoteEvent event);

    public void onFollow(FollowEvent event);

    default void onClose() {}

}
