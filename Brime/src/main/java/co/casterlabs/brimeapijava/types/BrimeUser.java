package co.casterlabs.brimeapijava.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class BrimeUser {
    private String xid;

    private String email;

    private String username;

    @JsonField("display_name")
    private String displayname;

    @JsonField("chat_color")
    private String chatColor;

    @JsonField("chat_lang")
    private String chatLanguage;

    @JsonField("created")
    private Instant created;

}
