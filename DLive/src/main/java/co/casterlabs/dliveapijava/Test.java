package co.casterlabs.dliveapijava;

import java.util.Arrays;

import co.casterlabs.dliveapijava.realtime.DliveChat;
import co.casterlabs.dliveapijava.realtime.DliveChatListener;
import co.casterlabs.dliveapijava.realtime.events.DliveChatGift;
import co.casterlabs.dliveapijava.realtime.events.DliveChatHost;
import co.casterlabs.dliveapijava.realtime.events.DliveChatMessage;
import co.casterlabs.dliveapijava.realtime.events.DliveChatSender;
import co.casterlabs.dliveapijava.realtime.events.DliveChatSubscription;
import xyz.e3ndr.fastloggingframework.FastLoggingFramework;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class Test {

    static {
        FastLoggingFramework.setDefaultLevel(LogLevel.TRACE);
    }

    public static void main(String[] args) throws Exception {
        String clientId = args[0];
        String clientSecret = args[1];

        // https://dlive.tv/o/authorize?client_id=9280537588&redirect_uri=http%3A%2F%2F127.0.0.1&response_type=code&scope=identity+chat:write+emote:read+moderation:write+streamtemplate:read+streamtemplate:write
//        System.out.println(
//            DliveAuth.authorize(
//                "DJO-AVHHNN-BWWMGTF-S1G",
//                "http://127.0.0.1",
//                clientId,
//                clientSecret
//            ).getRefreshToken()
//        );

//        DliveAuth auth = new DliveAuth(
//            clientId, clientSecret,
//            "-----",
//            "http://127.0.0.1"
//        ).onNewRefreshToken((refreshToken) -> System.out.printf("New Refresh Token: %s\n", refreshToken));
//        System.out.printf("Scopes: %s\n", auth.getScope());
//        DliveUser me = new DliveGetMyselfRequest(auth).send();

        DliveAuth auth = new DliveAuth(clientId, clientSecret);

        DliveChat chat = new DliveChat(auth, "enderv");
        chat.setListener(new DliveChatListener() {

            @Override
            public void onMessage(DliveChatMessage chatMessage) {
                System.out.printf("Message: %s\n", chatMessage);
            }

            @Override
            public void onGift(DliveChatGift chatGift) {
                System.out.printf("Gift: %s\n", chatGift);
            }

            @Override
            public void onLive() {
                System.out.printf("Live.\n");
            }

            @Override
            public void onOffline() {
                System.out.printf("Offline.\n");
            }

            @Override
            public void onFollow(DliveChatSender follower) {
                System.out.printf("Follow: %s\n", follower);
            }

            @Override
            public void onSubscription(DliveChatSubscription subscription) {
                System.out.printf("Subscription: %s\n", subscription);
            }

            @Override
            public void onMessagesDelete(String[] ids) {
                System.out.printf("Messages Delete: %s\n", Arrays.toString(ids));
            }

            @Override
            public void onHost(DliveChatHost host) {
                System.out.printf("Host: %s\n", host);
            }
        });
        chat.connect();
    }

}
