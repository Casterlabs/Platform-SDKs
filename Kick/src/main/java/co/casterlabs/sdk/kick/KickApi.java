package co.casterlabs.sdk.kick;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;

public class KickApi {

    public static final Rson RSON = new Rson.Builder()
        .build();

    public static void init() {
        KickApi.class.getClassLoader().setPackageAssertionStatus("co.casterlabs.sdk.kick", true);
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
