package co.casterlabs.sdk.x.v2.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class Xv2StreamSource {
    public final String id = null;
    public final String name = null;

    @JsonField("broadcast_id")
    public final String broadcastId = null;

    @JsonField("rtmp_region")
    public final String rtmpRegion = null;

    @JsonField("rtmp_url")
    public final String rtmpUrl = null;

    @JsonField("rtmps_url")
    public final String rtmpsUrl = null;

    @JsonField("rtmp_stream_key")
    public final String rtmpStreamKey = null;

    @JsonField("is_stream_active")
    public final Boolean isStreamActive = null;

}
