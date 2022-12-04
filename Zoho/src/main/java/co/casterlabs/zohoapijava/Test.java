package co.casterlabs.zohoapijava;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.zohoapijava.requests.ZohoMailGetUserAccountDetailsRequest;
import co.casterlabs.zohoapijava.requests.ZohoMailSendEmailRequest;
import co.casterlabs.zohoapijava.types.ZohoUserAccount;

public class Test {

    private static final String clientId = "1000.HPMEBOIWSFCXW9BBX11HUN09U4YK8T";
    private static final String redirectUri = "https://casterlabs.co/fakeauth";
    private static final String clientSecret = "44a6b5d4b25fb6d093cdb81e3bc2a7e64a93603ac3";
    private static final String scope = "ZohoMail.accounts.READ,ZohoMail.messages.CREATE";

    private static final String refreshToken = "1000.3f527c49b911d7521848e6411f75e123.1f4d473e03a7265cc9010bbf15c0305d";

    public static void main(String[] args) throws Exception {
//        getAuthUrl();
//        verifyOAuthCode("1000.a01ccff8148fa68f29976f9d33da419e.6c9f4c62dc767ebe7ddf09dd5585e5e0");
        sendEmail();
    }

    public static void sendEmail() throws Exception {
        ZohoAuth auth = new ZohoAuth(refreshToken, clientId, clientSecret, redirectUri, scope);

        ZohoUserAccount account = new ZohoMailGetUserAccountDetailsRequest(auth).send().get(0);

        String accountId = account.getAccountId();
        String fromAddress = account.getPrimaryEmailAddress();

        new ZohoMailSendEmailRequest(auth)
            .setAccountId(accountId)
            .setContentsAsHtml("<!DOCTYPE html><html>Test email!</html>")
            .setFromAddress(fromAddress)
            .setToAddress("pigman20042014@gmail.com")
            .setSubject("Test!")
            .send();
    }

    public static void getAuthUrl() {
        System.out.println(ZohoAuth.getOAuthOfflineUrl(scope, clientId, redirectUri));
    }

    public static void verifyOAuthCode(String code) throws ApiAuthException {
        ZohoAuth auth = ZohoAuth.verifyOAuthCode(code, clientId, clientSecret, redirectUri, scope);

        System.out.println(auth.getRefreshToken());
    }

}
