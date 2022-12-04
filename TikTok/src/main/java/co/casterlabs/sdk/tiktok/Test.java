package co.casterlabs.sdk.tiktok;

import java.io.File;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.sdk.tiktok.unsupported.chat.TiktokChatScraper;
import co.casterlabs.sdk.tiktok.unsupported.chat.TiktokScrapeChatListener;
import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeChatEvent;
import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeFollowEvent;
import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeGiftEvent;
import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeLikeEvent;
import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeShareEvent;
import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeStickerEvent;
import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeSubscriptionEvent;
import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeTreasureChestEvent;
import co.casterlabs.sdk.tiktok.unsupported.chat.types.TiktokScrapeViewersEvent;
import xyz.e3ndr.fastloggingframework.FastLoggingFramework;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

@SuppressWarnings("deprecation")
public class Test {

    static {
        FastLoggingFramework.setDefaultLevel(LogLevel.TRACE);
    }

    public static void main(String[] args) throws Exception {
        String clientId = args[0];
        String clientSecret = args[1];

        // https://www.tiktok.com/auth/authorize?client_key=awf51v5uexddtow9&redirect_uri=https%3A%2F%2Fcasterlabs.co%2Fauth&response_type=code&scope=user.info.basic%2Cvideo.list
//        System.out.println(
//            TiktokAuth.authorize(
//                "SKN2t_-ZSB7AJ6GKuwItxp1u9touPMAe8yRxp28XaT8ahry2d4bf_-pZcYMXEAs-rO-FXeNGbR6yIsoZ-gnwssBmgRNCFStuOIibbZjNLOc*3!6428",
//                "https://casterlabs.co/auth",
//                clientId,
//                clientSecret
//            ).getRefreshToken()
//        );

//        TiktokAuth auth = new TiktokAuth(
//            clientId, clientSecret,
//            "rft.79bb02fe3ef38c1fd15feef43bf1ccf6mH3LCAhsb9nAaSD2ThK8zawQCdvE!6447",
//            "https://casterlabs.co/auth"
//        ).onNewRefreshToken((refreshToken) -> System.out.printf("New Refresh Token: %s\n", refreshToken));
//        System.out.printf("Scopes: %s\n", auth.getScope());
//
//        TiktokUserInfo user = new TiktokGetUserInfoRequest(auth).send();
//        System.out.println(user);
//
//        System.out.printf("Handle: %s\n", new TiktokGetUserHandleRequest().setUserInfo(user).send());

        TiktokChatScraper.setupEnvironment(new File("wrapper"));

        TiktokChatScraper.listen("ethereal.in.e", new TiktokScrapeChatListener() {

            @Override
            public void onStart() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onEnd(@Nullable String reason) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(String error) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onChat(TiktokScrapeChatEvent event) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onGift(TiktokScrapeGiftEvent event) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onViewers(TiktokScrapeViewersEvent event) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLike(TiktokScrapeLikeEvent event) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFollow(TiktokScrapeFollowEvent event) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onShare(TiktokScrapeShareEvent event) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onSticker(TiktokScrapeStickerEvent event) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTreasureChest(TiktokScrapeTreasureChestEvent event) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onQuestion(TiktokScrapeChatEvent event) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onSubscription(TiktokScrapeSubscriptionEvent event) {
                // TODO Auto-generated method stub

            }
        });

    }

}
