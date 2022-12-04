package co.casterlabs.sdk.twitch.pubsub.messages;

import co.casterlabs.sdk.twitch.pubsub.PubSubTopic;

public interface PubSubMessage {

    public PubSubTopic getType();

}
