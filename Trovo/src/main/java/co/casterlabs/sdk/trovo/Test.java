package co.casterlabs.sdk.trovo;

import co.casterlabs.sdk.trovo.requests.TrovoGetChannelInfoRequest;
import co.casterlabs.sdk.trovo.requests.data.TrovoChannelInfo;

public class Test {

    // https://open.trovo.live/page/login.html?client_id=47d7f05d03a222e3d470537020c1d1c1&response_type=code&scope=channel_details_self+manage_messages&redirect_uri=https%3A%2F%2Fcasterlabs.co/auth&state=statedata
    public static void main(String[] args) throws Exception {
        String clientId = "47d7f05d03a222e3d470537020c1d1c1";
        String clientSecret = "f5850e7e816e2b4ecdd58931ec06833e";
        String redirectUri = "https://casterlabs.co/auth";

        String refreshToken = "a8879a22a876dbfb7596b89fd3833934";

//        System.out.println(TrovoAuth.authorize("bb05be04b17cf8c29049b2455aa7eed0", redirectUri, clientId, clientSecret));

        TrovoAuth userAuth = new TrovoAuth(clientId, clientSecret, refreshToken);

        System.out.println(userAuth.getScope());

        TrovoChannelInfo info = new TrovoGetChannelInfoRequest(userAuth).send();
        System.out.println(info);
    }

}
