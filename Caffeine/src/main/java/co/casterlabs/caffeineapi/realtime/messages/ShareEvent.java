package co.casterlabs.caffeineapi.realtime.messages;

import co.casterlabs.caffeineapi.types.CaffeineUser;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
public class ShareEvent extends ChatEvent {

    public ShareEvent(@NonNull CaffeineUser sender, @NonNull String message, @NonNull String id) {
        super(sender, message, id);
    }

}
