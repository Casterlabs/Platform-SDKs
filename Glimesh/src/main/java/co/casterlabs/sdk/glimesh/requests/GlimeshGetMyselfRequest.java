package co.casterlabs.sdk.glimesh.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.glimesh.GlimeshAuth;
import co.casterlabs.sdk.glimesh.types.GlimeshUser;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.NonNull;
import okhttp3.Response;

public class GlimeshGetMyselfRequest extends AuthenticatedWebRequest<GlimeshUser, GlimeshAuth> {
    private static final String QUERY = "query{myself{" + GlimeshUser.GQL_DATA + "}}";

    public GlimeshGetMyselfRequest(@NonNull GlimeshAuth auth) {
        super(auth);
    }

    @Override
    protected GlimeshUser execute() throws ApiException, ApiAuthException, IOException {
        try (Response response = GlimeshHttpUtil.sendHttp(QUERY, this.auth)) {
            String body = response.body().string();

            JsonObject json = Rson.DEFAULT.fromJson(body, JsonObject.class);

            if (response.code() == 401) {
                throw new ApiAuthException(json.toString());
            } else if (json.containsKey("errors")) {
                throw new ApiAuthException(json.toString());
            } else {
                return Rson.DEFAULT.fromJson(
                    json
                        .getObject("data")
                        .get("myself"),
                    GlimeshUser.class
                );
            }
        } catch (JsonParseException e) {
            throw new IOException(e);
        }
    }

}
