package co.casterlabs.sdk.noice.requests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.noice.NoiceApiJava;
import co.casterlabs.sdk.noice.NoiceAuth;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class NoiceHttpUtil {
    private static OkHttpClient client = new OkHttpClient();

    static JsonObject sendHttp(NoiceAuth auth, String query, Map<String, Object> params) throws IOException, ApiException, ApiAuthException {
        RequestBody body = RequestBody.create(
            new JsonObject()
                .put("query", query)
                .put("variables", Rson.DEFAULT.toJson(params))
                .toString()
                .getBytes(StandardCharsets.UTF_8)
        );

        Request.Builder builder = new Request.Builder()
            .url(NoiceApiJava.API + "/query")
            .post(body)
            .header("Content-Type", "application/json");
        auth.authenticateRequest(builder);

        Request request = builder.build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            JsonObject responseJson = Rson.DEFAULT.fromJson(responseBody, JsonObject.class);

            if (response.code() == 401) {
                throw new ApiAuthException(responseJson.toString());
            } else if (responseJson.containsKey("errors")) {
                throw new ApiException(responseJson.toString());
            } else {
                return responseJson;
            }
        }
    }

}
