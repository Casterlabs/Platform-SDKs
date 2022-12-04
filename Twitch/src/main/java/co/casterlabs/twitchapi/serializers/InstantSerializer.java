package co.casterlabs.twitchapi.serializers;

import java.time.Instant;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.TypeResolver;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonString;
import lombok.NonNull;

public class InstantSerializer implements TypeResolver<Instant> {

    @Override
    public @Nullable Instant resolve(@NonNull JsonElement value, @NonNull Class<?> type) {
        return Instant.parse(
            value.getAsString()
        );
    }

    @Override
    public @Nullable JsonElement writeOut(@NonNull Instant value, @NonNull Class<?> type) {
        return new JsonString(value.toString());
    }

}
