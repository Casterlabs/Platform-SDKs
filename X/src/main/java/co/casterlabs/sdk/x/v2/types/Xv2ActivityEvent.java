package co.casterlabs.sdk.x.v2.types;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.SneakyThrows;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class Xv2ActivityEvent {

    @JsonField("event_uuid")
    public final String eventUuid = null;

    public final Filter filter = null;

    @JsonField("event_type")
    public final String eventType = null;

    public final String tag = null;

    public final JsonObject payload = null;

    @SneakyThrows
    public <T> T as(Class<T> type) {
        return Rson.DEFAULT.fromJson(this.payload, type);
    }

    @ToString
    @JsonClass(exposeAll = true)
    public static class Filter {

        @JsonField("user_id")
        public final String userId = null;

    }

}
