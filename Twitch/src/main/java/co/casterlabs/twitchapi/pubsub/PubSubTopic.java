package co.casterlabs.twitchapi.pubsub;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PubSubTopic {
    BITS_V2("channel-bits-events-v2"),
    CHANNEL_POINTS_V1("channel-points-channel-v1"),
    SUBSCRIPTIONS_V1("channel-subscribe-events-v1"),

    // Sources:
    // https://gist.github.com/Scrxtchy/95b6c4d51c24e4186b80ab53f2355cd6
    // https://github.com/twitch-rs/twitch_api/wiki/Implemented-Features#pubsub
    UNSUPPORTED_VIDEO_PLAYBACK("video-playback-by-id"),
    UNSUPPORTED_FOLLOWING("following"),

    ;

    private String topic;

}
