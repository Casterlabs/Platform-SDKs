package co.casterlabs.twitchapi.pubsub.messages;

import co.casterlabs.twitchapi.pubsub.PubSubTopic;

public interface PubSubMessage {

    public PubSubTopic getType();

}
