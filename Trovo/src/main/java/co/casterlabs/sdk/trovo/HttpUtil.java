package co.casterlabs.sdk.trovo;

import java.io.IOException;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class HttpUtil {
    private static final OkHttpClient client = new OkHttpClient();

    public static JsonObject sendHttpGet(@NonNull String address, @Nullable TrovoAuth auth) throws IOException, ApiAuthException, ApiException {
        Request.Builder builder = new Request.Builder()
            .url(address)
            .get();

        if (auth != null) {
            auth.authenticateRequest(builder);
        }

        Request request = builder.build();

        try (Response response = client.newCall(request).execute()) {
            JsonObject responseBody = Rson.DEFAULT.fromJson(response.body().string(), JsonObject.class);

            if (responseBody.containsKey("status")) {
                int status = responseBody.get("status").getAsNumber().intValue();

                String reason = String.format("%s: %s", responseBody.get("error").getAsString(), responseBody.get("message").getAsString());

                if (status == 11704) {
                    throw new ApiAuthException(reason);
                } else {
                    throw new ApiException(reason);
                }
            }

            return responseBody;
        }
    }

    public static boolean httpExists(@NonNull String address) {
        Request request = new Request.Builder()
            .url(address)
            .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            return false;
        }
    }

}
