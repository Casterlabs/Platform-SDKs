package co.casterlabs.brimeapijava.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class BrimeChatter {

    @JsonField("user_xid")
    private String userXid;

    private String username;

    @JsonField("display_name")
    private String displayname;

    @JsonField("chat_color")
    private String chatColor;

    @JsonField("join_timestamp")
    private Instant joinTimestamp;

    @JsonField("guest")
    private boolean isGuest;

}
