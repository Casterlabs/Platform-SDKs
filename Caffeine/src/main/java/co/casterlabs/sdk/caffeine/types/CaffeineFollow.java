package co.casterlabs.sdk.caffeine.types;

import java.time.Instant;

import co.casterlabs.sdk.caffeine.requests.CaffeineUserInfoRequest;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CaffeineFollow {
    @JsonField("caid")
    private String CAID;

    @JsonField("followed_at")
    private Instant followedAt;

    public CaffeineUserInfoRequest getAsUser() {
        return new CaffeineUserInfoRequest()
            .setCAID(this.CAID);
    }

}
