package co.casterlabs.sdk.dlive.types;

import java.lang.reflect.Field;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class DliveUser {
    public static final String GQL_DATA = "avatar,displayname,username,createdAt,livestream{" + DliveLivestream.GQL_DATA + "},followers(first:0){" + DliveList.GQL_DATA + "}";

    public final String avatar = null;
    public final String displayname = null;
    public final String username = null;
    public final DliveLivestream livestream = null;
    public final DliveList followers = null;

    public final @JsonExclude Long createdAt = null;

    @JsonDeserializationMethod("createdAt")
    private void $deserialize_createdAt(JsonElement e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        long value;
        if (e.isJsonString()) {
            value = Long.parseLong(e.getAsString());
        } else {
            value = e.getAsNumber().longValue();
        }

        Field f = DliveUser.class.getField("createdAt");
        f.setAccessible(true);
        f.set(this, value);
    }

}
