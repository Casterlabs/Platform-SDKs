package co.casterlabs.sdk.tiktok.unsupported.realtime;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.Nullable;

import com.google.protobuf.ByteString;

import co.casterlabs.apiutil.realtime.JWSConnection;
import co.casterlabs.sdk.tiktok.unsupported.types.protobuf.synthetic.HeartbeatMessage;
import co.casterlabs.sdk.tiktok.unsupported.types.protobuf.synthetic.WebcastPushFrame;
import co.casterlabs.sdk.tiktok.unsupported.types.protobuf.webcast.BaseProtoMessage;
import co.casterlabs.sdk.tiktok.unsupported.types.protobuf.webcast.ProtoMessageFetchResult;
import co.casterlabs.sdk.tiktok.unsupported.types.protobuf.webcast.WebcastImEnterRoomMessage;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

@Accessors(chain = true)
public class TiktokWebcastImRoom extends JWSConnection {
    private static final long KEEPALIVE = TimeUnit.SECONDS.toMillis(10);

    private final ByteString hb;
    private final long roomId;

    private @Setter @Nullable TiktokWebcastImRoomListener listener;

    public TiktokWebcastImRoom(TiktokWebcastImRoomConnectionData imRoom) {
        this.setUri(URI.create(imRoom.url));
        this.setAdditionalHeaders(imRoom.headers);

        this.roomId = imRoom.roomId;
        this.hb = HeartbeatMessage.newBuilder()
            .setRoomId(imRoom.roomId)
            .build()
            .toByteString();
    }

    private void sendAck(WebcastPushFrame message, ProtoMessageFetchResult res) throws IOException {
        WebcastPushFrame frame = WebcastPushFrame.newBuilder()
            .setSeqId(0)
            .setLogId(message.getLogId())
            .setPayloadEncoding("pb")
            .setPayloadType("ack")
            .setPayload(res.getInternalExtBytes())
            .setService(0)
            .setMethod(0)
            .build();
        this.send(frame.toByteArray());
    }

    private void sendKeepAlive() throws IOException {
        WebcastPushFrame frame = WebcastPushFrame.newBuilder()
            .setSeqId(0)
            .setLogId(0)
            .setPayloadEncoding("pb")
            .setPayloadType("hb")
            .setPayload(this.hb)
            .setService(0)
            .setMethod(0)
            .build();
        this.send(frame.toByteArray());
    }

    @SneakyThrows
    @Override
    protected void onOpen() {
        this.sendKeepAlive();

        WebcastImEnterRoomMessage enterMessage = WebcastImEnterRoomMessage.newBuilder()
            .setRoomId(this.roomId)
            .setRoomTag("")
            .setLiveRegion("")
            .setLiveId(12)
            .setIdentity("audience")
            .setCursor("")
            .setAccountType(0)
            .setEnterUniqueId(0)
            .setFilterWelcomeMsg("0")
            .setIsAnchorContinueKeepMsg(false)
            .build();

        WebcastPushFrame frame = WebcastPushFrame.newBuilder()
            .setSeqId(0)
            .setLogId(0)
            .setPayloadEncoding("pb")
            .setPayloadType("im_enter_room")
            .setPayload(enterMessage.toByteString())
            .setService(0)
            .setMethod(0)
            .build();
        this.send(frame.toByteArray());

        this.doKeepAlive(KEEPALIVE, () -> {
            try {
                this.sendKeepAlive();
            } catch (IOException ignored) {}
        });

        if (this.listener != null) {
            this.listener.onOpen();
        }
    }

    @Override
    protected void onMessage(byte[] raw) {
        try {
            WebcastPushFrame message = WebcastPushFrame.parseFrom(raw);

            ProtoMessageFetchResult res;
            if ("pb".equals(message.getPayloadEncoding()) && !message.getPayload().isEmpty()) {
                byte[] bytes = message.getPayload().toByteArray();
                res = ProtoMessageFetchResult.parseFrom(bytes);
            } else {
                return;
            }

            if (res.getNeedsAck()) {
                this.sendAck(message, res);
            }

            if (this.listener != null) {
                for (BaseProtoMessage bpm : res.getMessagesList()) {
                    Object event = TiktokWebcastEventUtil.parseEvent(bpm);
                    if (event == null) continue;

                    this.listener.onEvent(event);
                    TiktokWebcastEventUtil.broadcastEvent(this.listener, event);
                }
            }
        } catch (Throwable t) {
            FastLogger.logStatic(LogLevel.SEVERE, "An uncaught exception occurred whilst processing frame: %s\n%s", raw, t);
        }
    }

    @Override
    protected void onClose(boolean remote) {
        if (listener != null) {
            listener.onClose(remote);
        }
    }

}
