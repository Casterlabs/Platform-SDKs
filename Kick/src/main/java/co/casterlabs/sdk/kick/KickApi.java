package co.casterlabs.sdk.kick;

import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;

public class KickApi {
    public static String API_BASE_URL = "https://kick.com"; // You can override this with a proxy of your choice ;)
    public static final String PUSHER_CLUSTER = "us2";
    public static final String PUSHER_KEY = "eb1d5f283081a78b932c";

    private static boolean hasInitialized = false;

    public static void init() {
        if (hasInitialized) return;

        KickApi.class.getClassLoader().setPackageAssertionStatus("co.casterlabs.sdk.kick", true);
        hasInitialized = true;
    }

    public static String parseResponsiveImage(JsonElement e) {
        if ((e == null) || e.isJsonNull()) {
            return null;
        } else if (e.isJsonString()) {
            return e.getAsString();
        }

        JsonObject obj = e.getAsObject();

        // The string kinda looks like this:
        // https://stream.kick.com/thumbnails/[...].webp 1920w, [...]
        String responsive = obj.getString("responsive");
        if (responsive != null) {
            int firstSpaceAt = responsive.indexOf(' ');
            if (firstSpaceAt != -1) {
                return responsive.substring(0, firstSpaceAt);
            }
        }

        return obj.getString("url"); // fallback.
    }

}
