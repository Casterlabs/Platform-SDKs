package co.casterlabs.twitchapi.helix.webhooks;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class WebhookUtil {
    private static final char[] HEX = "0123456789ABCDEF".toCharArray();

    public static String createSignatureWithSHA256(String secret, String payload) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");

        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(payload.getBytes());

        return new String(encode(rawHmac));
    }

    private static char[] encode(byte[] bytes) {
        int amount = bytes.length;
        char[] result = new char[2 * amount];

        int j = 0;
        for (int i = 0; i < amount; i++) {
            result[j++] = HEX[(0xF0 & bytes[i]) >>> 4];
            result[j++] = HEX[(0x0F & bytes[i])];
        }

        return result;
    }

}
