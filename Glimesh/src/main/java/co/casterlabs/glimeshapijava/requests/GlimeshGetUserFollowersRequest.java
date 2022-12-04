package co.casterlabs.glimeshapijava.requests;

import java.io.IOException;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.glimeshapijava.GlimeshAuth;
import co.casterlabs.glimeshapijava.types.GlimeshFollower;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.NonNull;
import okhttp3.Response;

public class GlimeshGetUserFollowersRequest extends AuthenticatedWebRequest<List<GlimeshFollower>, GlimeshAuth> {
    private static final String QUERY_BASE = "query{followers(%s){" + GlimeshFollower.GQL_DATA + "}}";
    private static final TypeToken<List<GlimeshFollower>> TYPE = new TypeToken<List<GlimeshFollower>>() {
    };

    private String queryArgs;

    public GlimeshGetUserFollowersRequest(@NonNull GlimeshAuth auth) {
        super(auth);
    }

    // Broken.
//    public GlimeshGetUserFollowersRequest queryByUserId(@NonNull String id) {
//        this.queryArgs = String.format("streamerId: %s", id);
//        return this;
//    }

    public GlimeshGetUserFollowersRequest queryByUsername(@NonNull String username) {
        this.queryArgs = String.format("streamerUsername: \"%s\"", username);
        return this;
    }

    @Override
    protected List<GlimeshFollower> execute() throws ApiException, ApiAuthException, IOException {
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
                    json.getObject("data")
                        .get("followers"),
                    TYPE
                );
            }
        } catch (JsonParseException e) {
            throw new IOException(e);
        }
    }

}
