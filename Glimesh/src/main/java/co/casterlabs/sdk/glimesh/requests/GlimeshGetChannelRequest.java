package co.casterlabs.sdk.glimesh.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.glimesh.GlimeshAuth;
import co.casterlabs.sdk.glimesh.types.GlimeshChannel;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.NonNull;
import okhttp3.Response;

public class GlimeshGetChannelRequest extends AuthenticatedWebRequest<GlimeshChannel, GlimeshAuth> {
    private static final String QUERY_BASE = "query{channel(%s){" + GlimeshChannel.GQL_DATA + "}}";

    private String queryArgs;

    public GlimeshGetChannelRequest(@NonNull GlimeshAuth auth) {
        super(auth);
    }

    public GlimeshGetChannelRequest queryByChannelId(@NonNull String id) {
        this.queryArgs = String.format("id: %s", id);
        return this;
    }

    public GlimeshGetChannelRequest queryByUsername(@NonNull String username) {
        this.queryArgs = String.format("username: \"%s\"", username);
        return this;
    }

    @Override
    protected GlimeshChannel execute() throws ApiException, ApiAuthException, IOException {
        String query = String.format(QUERY_BASE, this.queryArgs);

        try (Response response = GlimeshHttpUtil.sendHttp(query, this.auth)) {
            String body = response.body().string();

            JsonObject json = Rson.DEFAULT.fromJson(body, JsonObject.class);

            if (response.code() == 401) {
                throw new ApiAuthException(json.toString());
            } else if (json.containsKey("errors")) {
                throw new ApiException(json.toString());
            } else {
                return Rson.DEFAULT.fromJson(
                    json
                        .getObject("data")
                        .get("channel"),
                    GlimeshChannel.class
                );
            }
        } catch (JsonParseException e) {
            throw new IOException(e);
        }
    }

}
