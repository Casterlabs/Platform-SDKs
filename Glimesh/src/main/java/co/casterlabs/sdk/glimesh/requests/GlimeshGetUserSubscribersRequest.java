package co.casterlabs.sdk.glimesh.requests;

import java.io.IOException;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.glimesh.GlimeshAuth;
import co.casterlabs.sdk.glimesh.types.GlimeshSubscriber;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.NonNull;
import okhttp3.Response;

public class GlimeshGetUserSubscribersRequest extends AuthenticatedWebRequest<List<GlimeshSubscriber>, GlimeshAuth> {
    private static final String QUERY_BASE = "query{subscriptions(%s){" + GlimeshSubscriber.GQL_DATA + "}}";
    private static final TypeToken<List<GlimeshSubscriber>> TYPE = new TypeToken<List<GlimeshSubscriber>>() {
    };

    private String queryArgs;

    public GlimeshGetUserSubscribersRequest(@NonNull GlimeshAuth auth) {
        super(auth);
    }

//    public GlimeshGetUserSubscribersRequest queryByStreamerId(@NonNull String id) {
//        this.queryArgs = String.format("streamerId: %s", id);
//        return this;
//    }

    public GlimeshGetUserSubscribersRequest queryByUsername(@NonNull String username) {
        this.queryArgs = String.format("streamerUsername: \"%s\"", username);
        return this;
    }

    @Override
    protected List<GlimeshSubscriber> execute() throws ApiException, ApiAuthException, IOException {
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
                        .get("subscriptions"),
                    TYPE
                );
            }
        } catch (JsonParseException e) {
            throw new IOException(e);
        }
    }

}
