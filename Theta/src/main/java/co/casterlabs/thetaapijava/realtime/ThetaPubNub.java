package co.casterlabs.thetaapijava.realtime;

import java.io.Closeable;
import java.util.Collections;

import org.jetbrains.annotations.NotNull;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.UserId;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.objects_api.channel.PNChannelMetadataResult;
import com.pubnub.api.models.consumer.objects_api.membership.PNMembershipResult;
import com.pubnub.api.models.consumer.objects_api.uuid.PNUUIDMetadataResult;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import com.pubnub.api.models.consumer.pubsub.PNSignalResult;
import com.pubnub.api.models.consumer.pubsub.files.PNFileEventResult;
import com.pubnub.api.models.consumer.pubsub.message_actions.PNMessageActionResult;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.thetaapijava.ThetaAuth;
import co.casterlabs.thetaapijava.realtime.events.ThetaChatHello;
import co.casterlabs.thetaapijava.realtime.events.ThetaChatMessage;
import lombok.NonNull;
import lombok.SneakyThrows;

public class ThetaPubNub implements Closeable {
    private PubNub pubnub;

    @SneakyThrows
    public ThetaPubNub(@NonNull ThetaAuth auth, @NonNull String pubNubKey, @NonNull ThetaPubNubListener listener) throws ApiAuthException {
        PNConfiguration pnConfiguration = new PNConfiguration(new UserId(auth.getUserId()))
            .setSubscribeKey(pubNubKey);

        this.pubnub = new PubNub(pnConfiguration);
        this.pubnub.addListener(new SubscribeCallback() {
            @Override
            public void status(@NotNull PubNub pubnub, @NotNull PNStatus pnStatus) {
                if (pnStatus.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
                    listener.onClose();
                } else if (pnStatus.getCategory() == PNStatusCategory.PNConnectedCategory) {
                    listener.onOpen();
                }
            }

            @SneakyThrows
            @Override
            public void message(@NotNull PubNub pubnub, @NotNull PNMessageResult pnMessageResult) {
                String messageString = pnMessageResult.getMessage().toString(); // Uses Gson, yuck.
                JsonObject message = Rson.DEFAULT.fromJson(messageString, JsonObject.class);

                String type = message.getString("type");

                switch (type) {
                    case "chat_message":
                        listener.onChatMessage(
                            Rson.DEFAULT
                                .fromJson(message.get("data"), ThetaChatMessage.class)
                        );
                        return;

                    case "hello_message":
                        listener.onChatHello(
                            Rson.DEFAULT
                                .fromJson(message.get("data"), ThetaChatHello.class)
                        );
                        return;

                    case "turn_on_channel":
                        listener.onChannelOnline();
                        return;

                    case "turn_off_channel":
                        listener.onChannelOffline();
                        return;

                    default:
                        System.out.printf("[ThetaPubNub] Unrecognized message type: %s\n%s\n", type, messageString);
                        return;
                }
            }

            @Override
            public void presence(@NotNull PubNub pubnub, @NotNull PNPresenceEventResult pnPresenceEventResult) {}

            @Override
            public void signal(@NotNull PubNub pubnub, @NotNull PNSignalResult pnSignalResult) {}

            @Override
            public void uuid(@NotNull PubNub pubnub, @NotNull PNUUIDMetadataResult pnUUIDMetadataResult) {}

            @Override
            public void channel(@NotNull PubNub pubnub, @NotNull PNChannelMetadataResult pnChannelMetadataResult) {}

            @Override
            public void membership(@NotNull PubNub pubnub, @NotNull PNMembershipResult pnMembershipResult) {}

            @Override
            public void messageAction(@NotNull PubNub pubnub, @NotNull PNMessageActionResult pnMessageActionResult) {}

            @Override
            public void file(@NotNull PubNub pubnub, @NotNull PNFileEventResult pnFileEventResult) {}
        });

        this.pubnub.subscribe()
            .channels(Collections.singletonList("chat." + auth.getUserId()))
            .execute();
    }

    @Override
    public void close() {
        this.pubnub.disconnect();
    }

}
