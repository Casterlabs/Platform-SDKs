package co.casterlabs.sdk.kick.types;

import java.util.List;

import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.ToString;

@ToString
@JsonClass(exposeAll = true)
public class KickIntrospection {
    public static final TypeToken<KickIntrospection[]> ARRAY_TYPE = TypeToken.of(KickIntrospection[].class);
    public static final TypeToken<List<KickIntrospection>> LIST_TYPE = new TypeToken<List<KickIntrospection>>() {
    };

    public final Boolean active = null;
    public final Integer exp = null;
    public final String scope = null;

    @JsonField("client_id")
    public final String clientId = null;

    @JsonField("token_type")
    public final String tokenType = null;

}
