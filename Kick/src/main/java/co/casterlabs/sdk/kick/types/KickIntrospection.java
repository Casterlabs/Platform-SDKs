package co.casterlabs.sdk.kick.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;

@JsonClass(exposeAll = true)
public class KickIntrospection {
    public final Boolean active = null;
    public final Integer exp = null;
    public final String scope = null;

    @JsonField("client_id")
    public final String clientId = null;

    @JsonField("token_type")
    public final String tokenType = null;

}
