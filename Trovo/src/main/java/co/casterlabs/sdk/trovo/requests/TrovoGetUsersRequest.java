package co.casterlabs.sdk.trovo.requests;

import java.io.IOException;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.trovo.TrovoAuth;
import co.casterlabs.sdk.trovo.requests.data.TrovoUser;
import lombok.NonNull;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class TrovoGetUsersRequest extends AuthenticatedWebRequest<List<TrovoUser>, TrovoAuth> {
    public static final String URL = "https://open-api.trovo.live/openplatform/getusers";

    private static final TypeToken<List<TrovoUser>> LIST_TYPE = new TypeToken<List<TrovoUser>>() {
    };

    private JsonArray users = new JsonArray();

    public TrovoGetUsersRequest(@NonNull TrovoAuth auth, @NonNull String... usernames) {
        super(auth);

        for (String username : usernames) {
            this.users.add(username);
        }
    }

    public TrovoGetUsersRequest addUsers(@NonNull String... usernames) {
        for (String username : usernames) {
            assert username != null : "You cannot add a null username.";
            this.users.add(username);
        }

        return this;
    }

    @Override
    protected List<TrovoUser> execute() throws ApiException, ApiAuthException, IOException {
        assert !this.users.isEmpty() : "You must specify at least one username to query.";

        JsonObject body = new JsonObject()
            .put("user", users);

        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(URL)
                .post(
                    RequestBody.create(
                        body.toString(),
                        MediaType.get("application/json")
                    )
                ),
            this.auth
        );

        JsonObject json = Rson.DEFAULT.fromJson(response, JsonObject.class);

        return Rson.DEFAULT.fromJson(json.get("users"), LIST_TYPE);
    }

}
