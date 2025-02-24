package co.casterlabs.sdk.dlive.types;

import java.lang.reflect.Field;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class DliveLivestream {
    public static final String GQL_DATA = "thumbnailUrl,title,createdAt,category{" + DliveCategory.GQL_DATA + "},language{" + DliveLanguage.GQL_DATA + "}";

    public final String thumbnailUrl = null;
    public final String title = null;
    public final DliveCategory category = null;
    public final DliveLanguage language = null;

    public final @JsonExclude Long createdAt = null;

    @JsonDeserializationMethod("createdAt")
    private void $deserialize_createdAt(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        long value;
        if (e.isJsonString()) {
            value = Long.parseLong(e.getAsString());
        } else {
            value = e.getAsNumber().longValue();
        }

        Field f = DliveLivestream.class.getField("createdAt");
        f.setAccessible(true);
        f.set(this, value);
    }

}
