package co.casterlabs.glimeshapijava;

import co.casterlabs.glimeshapijava.requests.GlimeshGetChannelRequest;
import co.casterlabs.glimeshapijava.requests.GlimeshGetMyselfRequest;
import co.casterlabs.glimeshapijava.types.GlimeshChannel;
import co.casterlabs.glimeshapijava.types.GlimeshUser;
import xyz.e3ndr.fastloggingframework.FastLoggingFramework;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class Test {

    static {
        FastLoggingFramework.setDefaultLevel(LogLevel.TRACE);
    }

    public static void main(String[] args) throws Exception {
        String clientId = args[0];
        String clientSecret = args[1];

        // https://glimesh.tv/oauth/authorize?client_id=0a98554b-4fef-4af1-ba77-3f1f17ab16f6&redirect_uri=http%3A%2F%2F127.0.0.1&response_type=code&scope=public+email+chat
//        System.out.println(
//            GlimeshAuth.authorize(
//                "spYbemL5QuJsWr6w6l0hrFAiDsBcjoSdisPrJLyUWc1eFqbKqG9yrJyRLPZlMisBytWE3gEUVXAmiAkSfe80Es",
//                "http://127.0.0.1",
//                clientId,
//                clientSecret
//            ).getRefreshToken()
//        );

        GlimeshAuth auth = new GlimeshAuth(clientId, clientSecret, "zrbrRGisdHQMHvgv7wtZy51bnZbLLoZDuL9I33uv9ZNKC1MQZlT441pSOCPJLhGTu1rE14bsh6BwYckhgZQtwg", "http://127.0.0.1")
            .onNewRefreshToken((refreshToken) -> System.out.printf("New Refresh Token: %s\n", refreshToken));

        System.out.printf("Scopes: %s\n", auth.getScope());

        GlimeshUser user = new GlimeshGetMyselfRequest(auth).send();
        GlimeshChannel channel = new GlimeshGetChannelRequest(auth).queryByUsername(user.getUsername()).send();

        System.out.println(user.getId());
        System.out.println(channel.getId());

        /*
        int channelId = new GlimeshGetChannelRequest(auth, "lcyx").send().getId();
        String message = "Test Message";
        new GlimeshSendChatMessageRequest(auth, message, channelId)
            .send();
        
        GlimeshRealtimeChat conn = new GlimeshRealtimeChat(auth, new GlimeshGetChannelRequest(auth, "lcyx").send());
        
        conn.setListener(new GlimeshChatListener() {
        
            @SneakyThrows
            @Override
            public void onChat(GlimeshChatMessage chat) {
                FastLogger.logStatic("%s: %s\n", chat.getUser().getDisplayname(), chat.getMessage());
        
                if (chat.getMessage().equals("!r")) {
                    FastLogger.logStatic("Reconnecting");
                    conn.close();
                }
            }
        
            @Override
            public void onClose(boolean remote) {
                FastLogger.logStatic("Closed");
                conn.connect();
            }
        
        });
        conn.connectBlocking();
        
        Thread.sleep(5000);
        conn.close();*/

    }

}
