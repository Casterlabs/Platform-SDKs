package co.casterlabs.dliveapijava.requests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.dliveapijava.DliveApiJava;
import co.casterlabs.dliveapijava.DliveAuth;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class DliveHttpUtil {
    private static OkHttpClient client = new OkHttpClient();

    static JsonObject sendHttp(@NonNull String query, @Nullable DliveAuth auth) throws IOException, ApiException, ApiAuthException {
        RequestBody body = RequestBody.create(
            DliveApiJava
                .formatQuery(query)
                .getBytes(StandardCharsets.UTF_8)
        );

        Request.Builder builder = new Request.Builder()
            .url(DliveApiJava.API)
            .post(body)
            .header("Content-Type", "application/json");

        if (auth != null) {
            auth.authenticateRequest(builder);
        }

        Request request = builder.build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            JsonObject responseJson = DliveApiJava.RSON.fromJson(responseBody, JsonObject.class);

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
