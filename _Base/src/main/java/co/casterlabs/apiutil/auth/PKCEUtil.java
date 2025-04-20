package co.casterlabs.apiutil.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import lombok.AllArgsConstructor;

public class PKCEUtil {

    public static String generateChallenge(ChallengeMethod method, String verifier) {
        try {
            switch (method) {
                case SHA256:
                    return generateS256Challenge(verifier);

                case PLAIN:
                    return verifier;

                default:
                    throw new IllegalArgumentException("Unsupported code challenge method: " + method);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Unable to generate code challenge", e);
        }
    }

    private static String generateS256Challenge(String verifier) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(verifier.getBytes(StandardCharsets.US_ASCII));

        byte[] digest = messageDigest.digest();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }

    @AllArgsConstructor
    public static enum ChallengeMethod {
        SHA256("S256"),
        PLAIN("plain"),
        ;

        private String str;

        @Override
        public String toString() {
            return this.str;
        }
    }

}
