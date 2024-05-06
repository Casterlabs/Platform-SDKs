package co.casterlabs.sdk.dlive;

import co.casterlabs.rakurai.json.element.JsonObject;

public class DliveApiJava {
    public static final String API = "https://api.dlive.tv";
    public static final String REALTIME_API = "wss://api-ws.dlive.tv";

    static {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
    }

    public static String formatQuery(String query) {
        JsonObject payload = new JsonObject();

        payload.put("query", query);
        payload.put("variables", new JsonObject());

        return payload.toString();
    }

}
