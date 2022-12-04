package co.casterlabs.sdk.dlive;

import java.time.Instant;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeResolver;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.element.JsonString;
import lombok.NonNull;

public class DliveApiJava {
    public static final String API = "https://api.dlive.tv";
    public static final String REALTIME_API = "wss://api-ws.dlive.tv";

    public static final Rson RSON = new Rson.Builder()
        .registerTypeResolver(new TypeResolver<Instant>() {
            @Override
            public @Nullable Instant resolve(@NonNull JsonElement value, @NonNull Class<?> type) {
                long millis;

                if (value.isJsonString()) {
                    millis = Long.parseLong(value.getAsString());
                } else {
                    millis = value.getAsNumber().longValue();
                }

                return Instant.ofEpochMilli(millis);
            }

            @Override
            public @Nullable JsonElement writeOut(@NonNull Instant value, @NonNull Class<?> type) {
                return new JsonString(String.valueOf(value.toEpochMilli()));
            }
        }, Instant.class)
        .build();

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
