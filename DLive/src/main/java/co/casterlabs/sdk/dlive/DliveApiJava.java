package co.casterlabs.sdk.dlive;

import co.casterlabs.rakurai.json.element.JsonObject;

public class DliveApiJava {
    public static String API = "https://api.dlive.tv";
    public static String REALTIME_API = "wss://api-ws.dlive.tv";

    public static String formatQuery(String query) {
        JsonObject payload = new JsonObject();

        payload.put("query", query);
        payload.put("variables", new JsonObject());

        return payload.toString();
    }

}
