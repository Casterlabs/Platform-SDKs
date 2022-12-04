package co.casterlabs.glimeshapijava;

import co.casterlabs.rakurai.json.element.JsonObject;

public class GlimeshApiJava {
    public static final String GLIMESH_API = "https://glimesh.tv/api";
    public static final String GLIMESH_REALTIME_API = "wss://glimesh.tv/api/socket/websocket?vsn=2.0.0";

    public static String formatQuery(String query) {
        JsonObject payload = new JsonObject();

        payload.put("query", query);
        payload.put("variables", new JsonObject());

        return payload.toString();
    }

}
