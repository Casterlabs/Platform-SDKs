package co.casterlabs.caffeineapi.realtime.messages;

import co.casterlabs.caffeineapi.types.CaffeineUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class FollowEvent {
    private CaffeineUser follower;

}
