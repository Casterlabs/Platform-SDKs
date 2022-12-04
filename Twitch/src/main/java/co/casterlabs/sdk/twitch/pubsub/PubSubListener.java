package co.casterlabs.sdk.twitch.pubsub;

import co.casterlabs.sdk.twitch.pubsub.messages.PubSubMessage;

public interface PubSubListener {

    public void onError(PubSubError error);

    public void onMessage(PubSubMessage message);

}
