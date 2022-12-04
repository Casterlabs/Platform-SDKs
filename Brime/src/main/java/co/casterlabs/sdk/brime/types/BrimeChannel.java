package co.casterlabs.sdk.brime.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class BrimeChannel {
    private String xid;

    private String slug;

    private String status;

    @JsonField("display_name")
    private String displayname;

    @JsonField("stream_category_slug")
    private String streamCategorySlug;

    @JsonField("created")
    private Instant createdAt;

    private int followers;

    private boolean verified;

    private BrimeChannelOwner owner;
    private BrimeChannelStream stream;

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class BrimeChannelOwner {
        private String xid;
        @JsonField("email_verified")
        private boolean emailVerified;
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class BrimeChannelStream {
        private boolean live;
        private String title;
        private BrimeChannelStreamCategory category;
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class BrimeChannelStreamCategory {
        private String xid;
        private String name;
    }

}
