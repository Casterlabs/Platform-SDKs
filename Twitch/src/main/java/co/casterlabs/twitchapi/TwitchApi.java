package co.casterlabs.twitchapi;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import co.casterlabs.apiutil.ratelimit.BucketRateLimiter;
import co.casterlabs.apiutil.ratelimit.RateLimiter;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.twitchapi.pubsub.PubSubError;
import co.casterlabs.twitchapi.pubsub.messages.SubscriptionsV1TopicMessage.SubscriptionContext;
import co.casterlabs.twitchapi.pubsub.messages.SubscriptionsV1TopicMessage.SubscriptionPlan;
import co.casterlabs.twitchapi.serializers.InstantSerializer;
import co.casterlabs.twitchapi.serializers.PubSubErrorDeserializer;
import co.casterlabs.twitchapi.serializers.SubscriptionContextDeserializer;
import co.casterlabs.twitchapi.serializers.SubscriptionPlanDeserializer;
import lombok.Getter;

public class TwitchApi {
    // @formatter:off
    public static final Rson RSON = new Rson.Builder()
            .registerTypeResolver(new InstantSerializer(), Instant.class)
            .registerTypeResolver(new PubSubErrorDeserializer(), PubSubError.class)
            .registerTypeResolver(new SubscriptionPlanDeserializer(),SubscriptionPlan.class)
            .registerTypeResolver(new SubscriptionContextDeserializer(), SubscriptionContext.class)
            .build();
    // @formatter:on

    private static @Getter RateLimiter unauthenticatedRateLimiter = new BucketRateLimiter(30, 1, TimeUnit.MINUTES); // https://dev.twitch.tv/docs/api/guide#rate-limits

    static {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
    }

}
