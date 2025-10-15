package co.casterlabs.sdk.youtube.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.sdk.youtube.types.YoutubeLiveStreamCdn.IngestionType;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveStreamCdnIngestionInfo {
    public final String streamName = null;
    public final String ingestionAddress = null;
    public final String backupIngestionAddress = null;

    /**
     * @apiNote Only present for {@link IngestionType#RTMP} streams.
     */
    public final String rtmpsIngestionAddress = null;

    /**
     * @apiNote Only present for {@link IngestionType#RTMP} streams.
     */
    public final String rtmpsBackupIngestionAddress = null;

}
