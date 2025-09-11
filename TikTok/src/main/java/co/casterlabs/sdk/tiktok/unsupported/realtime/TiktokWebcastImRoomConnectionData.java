package co.casterlabs.sdk.tiktok.unsupported.realtime;

import java.util.Map;

import co.casterlabs.sdk.tiktok.unsupported.types.protobuf.webcast.ProtoMessageFetchResult;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class TiktokWebcastImRoomConnectionData {
    public final String url;
    public final long roomId;
    public final Map<String, String> headers;
    public final ProtoMessageFetchResult imFetch;

}
