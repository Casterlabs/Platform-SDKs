package co.casterlabs.sdk.dlive.realtime;

import java.io.IOException;
import java.net.URI;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.realtime.WSConnection;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.element.JsonString;
import co.casterlabs.sdk.dlive.DliveApiJava;
import co.casterlabs.sdk.dlive.DliveAuth;
import co.casterlabs.sdk.dlive.realtime.events.DliveChatGift;
import co.casterlabs.sdk.dlive.realtime.events.DliveChatHost;
import co.casterlabs.sdk.dlive.realtime.events.DliveChatMessage;
import co.casterlabs.sdk.dlive.realtime.events.DliveChatSubscription;
import co.casterlabs.sdk.dlive.realtime.events._DliveChatDelete;
import co.casterlabs.sdk.dlive.realtime.events._DliveChatFollow;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class DliveChat extends WSConnection {
    private @Setter @Nullable DliveChatListener listener;

    private DliveAuth auth;
    private String username;

    public DliveChat(@NonNull DliveAuth auth, @NonNull String username) {
        this.auth = auth;
        this.username = username;
        this.setUri(URI.create(DliveApiJava.REALTIME_API));
    }

    private void send(String type, JsonObject payload) throws IOException {
        JsonObject frame = new JsonObject()
            .put("type", type)
            .put("payload", payload);
        super.send(frame.toString());
    }

    @SneakyThrows
    @Override
    protected void onOpen() {
        this.send("connection_init", JsonObject.singleton("authorization", this.auth.getAccessToken()));

        if (this.listener != null) {
            this.listener.onOpen();
        }
    }

    @Override
    protected void onMessage(String raw) {

        try {
            JsonObject payload = Rson.DEFAULT.fromJson(raw, JsonObject.class);

            switch (payload.getString("type")) {
                case "connection_ack": {
                    this.send(
                        "start",
                        JsonObject.singleton(
                            "query",
                            String.format("subscription{streamMessageReceived(streamer:%s){__typename}}", new JsonString(username))
                        )
                    );
                    break;
                }

                case "data": {
                    if (listener == null) break;

                    for (JsonElement e : payload.getObject("payload").getObject("data").getArray("streamMessageReceived")) {
                        JsonObject item = e.getAsObject();

                        // https://dev.dlive.tv/schema/chattype.doc.html
                        switch (item.getString("type")) {

                            case "Message": {
                                listener.onMessage(Rson.DEFAULT.fromJson(item, DliveChatMessage.class));
                                break;
                            }

                            case "Gift": {
                                listener.onGift(Rson.DEFAULT.fromJson(item, DliveChatGift.class));
                                break;
                            }

                            case "Live": {
                                listener.onLive();
                                break;
                            }

                            case "Offline": {
                                listener.onOffline();
                                break;
                            }

                            case "Follow": {
                                listener.onFollow(Rson.DEFAULT.fromJson(item, _DliveChatFollow.class).sender);
                                break;
                            }

                            case "Subscription": {
                                listener.onSubscription(Rson.DEFAULT.fromJson(item, DliveChatSubscription.class));
                                break;
                            }

                            case "Delete": {
                                listener.onMessagesDelete(Rson.DEFAULT.fromJson(item, _DliveChatDelete.class).ids);
                                break;
                            }

                            case "Host": {
                                listener.onHost(Rson.DEFAULT.fromJson(item, DliveChatHost.class));
                                break;
                            }

                            // Unused.
                            case "ChatMode":
                            case "Ban":
                            case "Mod":
                            case "Emote":
                            case "Timeout": {
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        } catch (Throwable t) {
            FastLogger.logStatic(LogLevel.SEVERE, "An uncaught exception occurred whilst processing frame: %s\n%s", raw, t);
        }
    }

    @Override
    protected void onClose(boolean remote) {
        if (this.listener != null) {
            this.listener.onClose(remote);
        }
    }

}
