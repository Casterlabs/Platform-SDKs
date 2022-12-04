package co.casterlabs.sdk.tiktok.unsupported.chat;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeChatEvent;
import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeFollowEvent;
import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeGiftEvent;
import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeLikeEvent;
import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeShareEvent;
import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeStickerEvent;
import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeSubscriptionEvent;
import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeTreasureChestEvent;
import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeViewersEvent;

public interface TiktokScrapeChatListener {

    public void onStart();

    public void onEnd(@Nullable String reason);

    public void onError(String error);

    public void onChat(TiktokScrapeChatEvent event);

    public void onGift(TiktokScrapeGiftEvent event);

    public void onViewers(TiktokScrapeViewersEvent event);

    public void onLike(TiktokScrapeLikeEvent event);

    public void onFollow(TiktokScrapeFollowEvent event);

    public void onShare(TiktokScrapeShareEvent event);

    public void onSticker(TiktokScrapeStickerEvent event);

    public void onTreasureChest(TiktokScrapeTreasureChestEvent event);

    public void onQuestion(TiktokScrapeChatEvent event);

    public void onSubscription(TiktokScrapeSubscriptionEvent event);

}
