package co.casterlabs.twitchapi.serializers;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.TypeResolver;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonString;
import co.casterlabs.twitchapi.pubsub.PubSubError;
import lombok.NonNull;

public class PubSubErrorDeserializer implements TypeResolver<PubSubError> {

    @Override
    public @Nullable PubSubError resolve(@NonNull JsonElement value, @NonNull Class<?> type) {
        String str = value.getAsString();

        for (PubSubError error : PubSubError.values()) {
            if (error.name().equalsIgnoreCase(str)) {
                return error;
            }
        }

        return null;
    }

    @Override
    public @Nullable JsonElement writeOut(@NonNull PubSubError value, @NonNull Class<?> type) {
        return new JsonString(value.name());
    }

}
