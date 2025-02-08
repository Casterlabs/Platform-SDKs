package co.casterlabs.sdk.kick.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class KickPublishingInfo {

    @JsonField("rtmp_publish_path")
    private String rtmpPublishPath;

    @JsonField("rtmp_stream_token")
    private String rtmpStreamToken;

}
