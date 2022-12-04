package co.casterlabs.zohoapijava;

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
