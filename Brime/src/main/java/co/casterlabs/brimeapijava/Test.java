package co.casterlabs.brimeapijava;

import co.casterlabs.brimeapijava.realtime.BrimeChatMessage;
import co.casterlabs.brimeapijava.realtime.BrimeRealtime;
import co.casterlabs.brimeapijava.realtime.BrimeRealtimeListener;
import co.casterlabs.brimeapijava.requests.BrimeGetAccountRequest;
import co.casterlabs.brimeapijava.requests.BrimeGetChannelRequest;
import co.casterlabs.brimeapijava.types.BrimeChannel;
import co.casterlabs.brimeapijava.types.BrimeUser;

public class Test {

    // https://auth.brime.tv/authorize?client_id=l87k8wMUeyuotnCp9HFsOzQ4gTi66atj&redirect_uri=https%3A%2F%2Fcasterlabs.co%2Fauth&response_type=code&scope=offline_access&state=test
    public static void main(String[] args) throws Exception {
        String clientId = "l87k8wMUeyuotnCp9HFsOzQ4gTi66atj";
        String clientSecret = "_cRxKVjV0Gu2RLXbFAGhlJa1MscOqyUt8MiowcxN9TwQvMFbZ1b86qS5yoYFAxYl";
        String refreshToken = "YJBAN3shk11XvWJwlELgO_yb8o_OLuAkYgxnveyyH7VQo";

//        System.out.println(
//            BrimeAuth.authorize("rfJQWH4v3QO6253NlaVw8YGGmvgxTxv4aLhk_902GXrtB", "https://casterlabs.co/auth", clientId, clientSecret).getRefreshToken()
//        );

        BrimeAuth auth = new BrimeAuth(clientId, clientSecret, refreshToken);

        BrimeUser account = new BrimeGetAccountRequest(auth)
            .send();

        BrimeChannel channel = new BrimeGetChannelRequest()
            .queryBySlug(account.getUsername())
            .send();

        System.out.printf("Name: %s, Account XID: %s, Channel XID: %s\n", account.getDisplayname(), account.getXid(), channel.getXid());

        @SuppressWarnings("resource")
        BrimeRealtime conn = new BrimeRealtime(channel, auth);
        conn.setListener(new BrimeRealtimeListener() {

            @Override
            public void onOpen() {
                System.out.println("open");
            }

            @Override
            public void onChat(BrimeChatMessage chat) {
                System.out.printf("%s > %s\n", chat.getSender().getDisplayname(), chat.getContent().getRaw());
            }

            @Override
            public void onChatClear() {
                System.out.println("clear");
            }

            @Override
            public void onChatMessageDelete(String xid) {
                System.out.printf("delete, xid: %s\n", xid);
            }

            @Override
            public void onChannelWentLive() {
                System.out.println("live");
            }

            @Override
            public void onChannelWentOffline() {
                System.out.println("offline");
            }

            @Override
            public void onViewerJoin(String xid) {
                System.out.printf("viewer join, xid: %s\n", xid);
            }

            @Override
            public void onViewerLeave(String xid) {
                System.out.printf("viewer leave, xid: %s\n", xid);
            }

            @Override
            public void onClose(boolean remote) {
                System.out.println("closed, remote:" + remote);
            }

        })
            .connect();
    }

}
