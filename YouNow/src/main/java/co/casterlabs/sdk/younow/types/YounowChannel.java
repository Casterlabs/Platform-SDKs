package co.casterlabs.sdk.younow.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class YounowChannel {
    private int userId;

    private @JsonField("profile") String username;

    private String description;

    private @JsonField("totalFans") int fans;

    private @JsonField("totalSubscribers") int subscribersCount;

}
