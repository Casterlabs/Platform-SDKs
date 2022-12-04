package co.casterlabs.caffeineapi.realtime.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@NonNull
@ToString
@AllArgsConstructor
public class UpvoteEvent {
    private ChatEvent event;
    private int upvotes;

}
