package co.casterlabs.sdk.zoho;

public class ZohoUtil {

    public static boolean verifyNotNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                return false;
            }
        }

        return true;
    }

}
