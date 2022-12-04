package co.casterlabs.twitchapi.serializers;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.TypeResolver;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonString;
import co.casterlabs.twitchapi.pubsub.messages.SubscriptionsV1TopicMessage.SubscriptionContext;
import lombok.NonNull;

public class SubscriptionContextDeserializer implements TypeResolver<SubscriptionContext> {

    @Override
    public @Nullable SubscriptionContext resolve(@NonNull JsonElement value, @NonNull Class<?> type) {
        return SubscriptionContext.valueOf(
            value
                .getAsString()
                .toUpperCase()
        );
    }

    @Override
    public @Nullable JsonElement writeOut(@NonNull SubscriptionContext value, @NonNull Class<?> type) {
        return new JsonString(value.name());
    }

}
