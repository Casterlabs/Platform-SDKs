package co.casterlabs.sdk.trovo.requests;

import java.io.IOException;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.trovo.TrovoAuth;
import co.casterlabs.sdk.trovo.requests.data.TrovoGameCategory;
import lombok.NonNull;
import okhttp3.Request;

public class TrovoGetGameCategoriesRequest extends AuthenticatedWebRequest<List<TrovoGameCategory>, TrovoAuth> {
    public static final String URL = "https://open-api.trovo.live/openplatform/categorys/top";

    private static final TypeToken<List<TrovoGameCategory>> LIST_TYPE = new TypeToken<List<TrovoGameCategory>>() {
    };

    public TrovoGetGameCategoriesRequest(@NonNull TrovoAuth auth) {
        super(auth);
    }

    @Override
    protected List<TrovoGameCategory> execute() throws ApiException, ApiAuthException, IOException {
        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(URL),
            this.auth
        );

        JsonObject json = Rson.DEFAULT.fromJson(response, JsonObject.class);

        return Rson.DEFAULT.fromJson(json.get("category_info"), LIST_TYPE);
    }

}
