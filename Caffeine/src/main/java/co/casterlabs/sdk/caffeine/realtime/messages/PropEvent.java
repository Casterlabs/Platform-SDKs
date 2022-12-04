package co.casterlabs.sdk.caffeine.realtime.messages;

import co.casterlabs.sdk.caffeine.types.CaffeineProp;
import co.casterlabs.sdk.caffeine.types.CaffeineUser;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
public class PropEvent extends ChatEvent {
    private CaffeineProp prop;
    private int amount;

    public PropEvent(@NonNull CaffeineUser sender, @NonNull String message, @NonNull String id, int amount, @NonNull CaffeineProp prop) {
        super(sender, message, id);

        this.prop = prop;
        this.amount = amount;
    }

}
