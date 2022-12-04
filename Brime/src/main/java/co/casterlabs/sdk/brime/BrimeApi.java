package co.casterlabs.sdk.brime;

import java.time.Instant;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeResolver;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonNumber;
import lombok.NonNull;

public class BrimeApi {

    public static final Rson RSON = new Rson.Builder()
        .registerTypeResolver(new TypeResolver<Instant>() {
            @Override
            public @Nullable Instant resolve(@NonNull JsonElement value, @NonNull Class<?> type) {
                if (value.isJsonNull()) {
                    return null;
                } else {
                    long longValue = value.getAsNumber().longValue();

                    // Very dirty way... But it works!
                    if (longValue < 5000000000l) {
                        return Instant.ofEpochSecond(longValue);
                    } else {
                        return Instant.ofEpochMilli(longValue);
                    }
                }
            }

            @Override
            public @Nullable JsonElement writeOut(@NonNull Instant value, @NonNull Class<?> type) {
                return new JsonNumber(value.toEpochMilli());
            }
        }, Instant.class)
        .build();

}
