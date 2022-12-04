package co.casterlabs.sdk.twitch;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import co.casterlabs.apiutil.ratelimit.BucketRateLimiter;
import co.casterlabs.apiutil.ratelimit.RateLimiter;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.sdk.twitch.pubsub.PubSubError;
import co.casterlabs.sdk.twitch.pubsub.messages.SubscriptionsV1TopicMessage.SubscriptionContext;
import co.casterlabs.sdk.twitch.pubsub.messages.SubscriptionsV1TopicMessage.SubscriptionPlan;
import co.casterlabs.sdk.twitch.serializers.InstantSerializer;
import co.casterlabs.sdk.twitch.serializers.PubSubErrorDeserializer;
import co.casterlabs.sdk.twitch.serializers.SubscriptionContextDeserializer;
import co.casterlabs.sdk.twitch.serializers.SubscriptionPlanDeserializer;
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
