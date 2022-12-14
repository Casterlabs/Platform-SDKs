package co.casterlabs.sdk.twitch.pubsub.networking;

import co.casterlabs.rakurai.json.element.JsonObject;

public abstract class Packet<T extends Enum<?>> {

    public abstract JsonObject serialize();

    public abstract T getPacketType();

}
