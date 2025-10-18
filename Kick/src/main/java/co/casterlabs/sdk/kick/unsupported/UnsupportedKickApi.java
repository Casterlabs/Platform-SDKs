package co.casterlabs.sdk.kick.unsupported;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;

public class UnsupportedKickApi {
    public static String API_BASE_URL = "https://kick.com"; // You can override this with a proxy of your choice ;)
    public static String PUSHER_CLUSTER = "us2";
    public static String PUSHER_KEY = "32cbd69e4b950bf97679";

    public static @Nullable String parseResponsiveImage(JsonElement e) {
        if ((e == null) || e.isJsonNull()) {
            return null;
        } else if (e.isJsonString()) {
            return e.getAsString();
        }

        JsonObject obj = e.getAsObject();

        try {
            if (obj.containsKey("responsive")) {
                return splitImage(obj.getString("responsive"));
            }
        } catch (Throwable ignored) {}

        try {
            if (obj.containsKey("srcset")) {
                return splitImage(obj.getString("srcset"));
            }
        } catch (Throwable ignored) {}

        try {
            if (obj.containsKey("url")) {
                return obj.getString("url");
            }
        } catch (Throwable ignored) {}

        try {
            if (obj.containsKey("src")) {
                return obj.getString("src");
            }
        } catch (Throwable ignored) {}

        return null;
    }

    private static String splitImage(String responsive) {
        // The string kinda looks like this:
        // https://stream.kick.com/thumbnails/[...].webp 1920w, [...]
        int firstSpaceAt = responsive.indexOf(' ');
        if (firstSpaceAt != -1) {
            return responsive.substring(0, firstSpaceAt);
        }
        throw new IllegalArgumentException();
    }

}
