package co.casterlabs.twitchapi.serializers;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.TypeResolver;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonString;
import co.casterlabs.twitchapi.pubsub.messages.SubscriptionsV1TopicMessage.SubscriptionPlan;
import lombok.NonNull;

public class SubscriptionPlanDeserializer implements TypeResolver<SubscriptionPlan> {

    @Override
    public @Nullable SubscriptionPlan resolve(@NonNull JsonElement value, @NonNull Class<?> type) {
        String str = value.getAsString();

        switch (str) {
            case "Prime":
            case "1":
                return SubscriptionPlan.PRIME;

            case "1000":
                return SubscriptionPlan.TIER_1;

            case "2000":
                return SubscriptionPlan.TIER_2;

            case "3000":
                return SubscriptionPlan.TIER_3;

            default:
                return SubscriptionPlan.UNKNOWN;
        }
    }

    @Override
    public @Nullable JsonElement writeOut(@NonNull SubscriptionPlan value, @NonNull Class<?> type) {
        return new JsonString(value.name());
    }

}
