package co.casterlabs.thetaapijava;

import co.casterlabs.thetaapijava.realtime.ThetaPubNub;
import co.casterlabs.thetaapijava.realtime.ThetaPubNubListener;
import co.casterlabs.thetaapijava.realtime.events.ThetaChatHello;
import co.casterlabs.thetaapijava.realtime.events.ThetaChatMessage;
import xyz.e3ndr.fastloggingframework.FastLoggingFramework;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class Test {

    static {
        FastLoggingFramework.setDefaultLevel(LogLevel.TRACE);
    }

    public static void main(String[] args) throws Exception {
        String clientId = "a19rt3rqyrijak5w9ebu3syumq0sh931";
        String clientSecret = "jhj9g3aq15s2sudqhkqajs2aw5mut3ef";
        String pubnubClientId = "sub-c-0d05b2d2-6fa7-11e6-8a59-0619f8945a4f";

        // https://www.theta.tv/account/grant-app?client_id=a19rt3rqyrijak5w9ebu3syumq0sh931&redirect_uri=https://casterlabs.co/auth
//        System.out.println(
//            ThetaAuth.authorize(
//                "py35dhdmu33sv6u6yh7pv66zve5k80bv",
//                clientId,
//                clientSecret
//            ).getRefreshToken()
//        );

        ThetaAuth auth = new ThetaAuth(clientId, clientSecret, "bqy705gdmcd3sgjzgq8qxdmcqvi0jjyp");

        String userId = auth.getUserId();
        System.out.printf("Authenticated User ID: %s\n", userId);

        ThetaPubNub pubnub = new ThetaPubNub(auth, pubnubClientId, new ThetaPubNubListener() {
            @Override
            public void onOpen() {
                System.out.println("Open.");
            }

            @Override
            public void onClose() {
                System.out.println("Close.");
            }

            @Override
            public void onChatMessage(ThetaChatMessage chatMessage) {
                System.out.println(chatMessage);
//                System.out.printf("%s > %s\n", chatMessage.getUser().getUsername(), chatMessage.getText());
            }

            @Override
            public void onChatHello(ThetaChatHello chatHello) {
                System.out.println(chatHello);
            }

            @Override
            public void onChannelOnline() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onChannelOffline() {
                // TODO Auto-generated method stub

            }
        });

    }

}
