package co.casterlabs.caffeineapi.realtime.messages;

import co.casterlabs.caffeineapi.types.CaffeineUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@NonNull
@ToString
@AllArgsConstructor
public class ChatEvent {
    private CaffeineUser sender;
    private String message;
    private String id;

}
