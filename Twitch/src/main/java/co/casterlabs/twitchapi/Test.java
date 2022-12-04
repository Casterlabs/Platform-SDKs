package co.casterlabs.twitchapi;

import co.casterlabs.twitchapi.helix.TwitchHelixAuth;
import co.casterlabs.twitchapi.helix.requests.HelixGetUserFollowersRequest;
import xyz.e3ndr.fastloggingframework.FastLoggingFramework;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

@SuppressWarnings("unused")
public class Test {
    // https://id.twitch.tv/oauth2/authorize?response_type=code&client_id=pubzluljki2j8ufvehtqr88iwxcsvp&redirect_uri=https%3A%2F%2Fx.x&force_verify=true&scope=channel:read:redemptions%20channel:read:subscriptions%20channel_subscriptions%20bits:read
    private static final String TWITCH_AUTH = "https://id.twitch.tv/oauth2/token?redirect_uri=%s&grant_type=authorization_code&client_secret=%s&client_id=%s&code=%s";
    private static final String SECRET = "rk788pufdcvqpg6fp1144fstg7s62x";
    private static final String ID = "pubzluljki2j8ufvehtqr88iwxcsvp";

    private static String REFRESH_TOKEN = "2tqup4eql47mbxm47bgdgmazsdbx28ama60kuwvlphidho6wop"; // itzlcyx

    public static void main(String[] args) throws Exception {
        FastLoggingFramework.setDefaultLevel(LogLevel.ALL);
//        getOAuthToken("2vkw52tory58g1omlogkqwqxu3k3gy");
        test();
    }

    /* ---------------- */
    /* Test    */
    /* ---------------- */

    private static void test() throws Exception {
        TwitchHelixAuth auth = new TwitchHelixAuth(ID, SECRET, REFRESH_TOKEN);
        final String channel = "71092938"; // xqc

        System.out.println(new HelixGetUserFollowersRequest(auth).setId(channel).setFirst(1).execute());

//        PubSubRouter router = new PubSubRouter();
//
//        router.subscribeTopic(
//            new PubSubListenRequest(auth, new PubSubListener() {
//                @Override
//                public void onError(PubSubError error) {
//                    System.out.println(error);
//                }
//
//                @Override
//                public void onMessage(PubSubMessage message) {
//                    System.out.println(message);
//                }
//            })
//                .addTopic(PubSubTopic.UNSUPPORTED_VIDEO_PLAYBACK, channel)
//                .addTopic(PubSubTopic.UNSUPPORTED_FOLLOWING, channel)
//        );
    }

    /* ---------------- */
    /* OAuth            */
    /* ---------------- */

    private static void getOAuthToken(String code) throws Exception {
        System.out.println(TwitchHelixAuth.authorize(code, "https://x.x", ID, SECRET));
    }

}
