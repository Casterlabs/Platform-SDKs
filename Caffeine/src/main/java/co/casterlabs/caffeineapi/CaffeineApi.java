package co.casterlabs.caffeineapi;

import java.time.Instant;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.caffeineapi.types.UserBadge;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeResolver;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonString;
import lombok.NonNull;

public class CaffeineApi {

    public static final Rson RSON = new Rson.Builder()
        .registerTypeResolver(new TypeResolver<Instant>() {
            @Override
            public @Nullable Instant resolve(@NonNull JsonElement value, @NonNull Class<?> type) {
                return Instant.parse(value.getAsString());
            }

            @Override
            public @Nullable JsonElement writeOut(@NonNull Instant value, @NonNull Class<?> type) {
                return new JsonString(value.toString());
            }
        }, Instant.class)
        .registerTypeResolver(new TypeResolver<UserBadge>() {
            @Override
            public @Nullable UserBadge resolve(@NonNull JsonElement value, @NonNull Class<?> type) {
                return UserBadge.from(value.isJsonNull() ? null : value.getAsString());
            }

            @Override
            public @Nullable JsonElement writeOut(@NonNull UserBadge value, @NonNull Class<?> type) {
                return new JsonString(value.name());
            }
        }, UserBadge.class)
        .build();

    static {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
    }

}
