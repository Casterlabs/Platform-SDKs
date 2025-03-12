package co.casterlabs.sdk.kick.webhook;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import lombok.SneakyThrows;

public class KickWebhooks {
    private static PublicKey publicKey;
    static {
        try {
            updatePublicKey(
                "-----BEGIN PUBLIC KEY-----\r\n"
                    + "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq/+l1WnlRrGSolDMA+A8\r\n"
                    + "6rAhMbQGmQ2SapVcGM3zq8ANXjnhDWocMqfWcTd95btDydITa10kDvHzw9WQOqp2\r\n"
                    + "MZI7ZyrfzJuz5nhTPCiJwTwnEtWft7nV14BYRDHvlfqPUaZ+1KR4OCaO/wWIk/rQ\r\n"
                    + "L/TjY0M70gse8rlBkbo2a8rKhu69RQTRsoaf4DVhDPEeSeI5jVrRDGAMGL3cGuyY\r\n"
                    + "6CLKGdjVEM78g3JfYOvDU/RvfqD7L89TZ3iN94jrmWdGz34JNlEI5hqK8dd7C5EF\r\n"
                    + "BEbZ5jgB8s8ReQV8H+MkuffjdAj3ajDDX3DOJMIut1lBrUVD1AaSrGCKHooWoL2e\r\n"
                    + "twIDAQAB\r\n"
                    + "-----END PUBLIC KEY-----"
            );
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void updatePublicKey(String publicKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Strip headers and other junk.
        publicKeyString = publicKeyString
            .replaceAll("-----BEGIN PUBLIC KEY-----", "")
            .replaceAll("-----END PUBLIC KEY-----", "")
            .replaceAll("\r", "")
            .replaceAll("\n", "");

        // Decode the publicKey b64.
        byte[] key = Base64.getDecoder().decode(publicKeyString);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);

        publicKey = keyFactory.generatePublic(keySpec);
    }

    @SneakyThrows
    public static boolean verify(String body, String kickEventSignatureHeader, String kickEventMessageIdHeader, String kickEventMessageTimestampHeader) {
        byte[] recreatedSignature = String.join(".", kickEventMessageIdHeader, kickEventMessageTimestampHeader, body).getBytes(StandardCharsets.UTF_8);
        byte[] eventSignature = Base64.getDecoder().decode(kickEventSignatureHeader);

        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(publicKey);
        verifier.update(recreatedSignature);
        return verifier.verify(eventSignature);
    }

    public static KickWebhookEvent deserialize(String body, String kickEventTypeHeader, String kickEventVersionHeader) throws JsonValidationException, JsonParseException {
        KickWebhookEvent.Type type = KickWebhookEvent.Type.get(kickEventTypeHeader, Integer.parseInt(kickEventVersionHeader));
        return Rson.DEFAULT.fromJson(body, type.clazz);
    }

}
