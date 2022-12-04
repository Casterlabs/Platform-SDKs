package co.casterlabs.sdk.caffeine.realtime.messages;

import co.casterlabs.sdk.caffeine.types.CaffeineUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class FollowEvent {
    private CaffeineUser follower;

}
