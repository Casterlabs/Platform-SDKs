package co.casterlabs.sdk.theta.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.sdk.theta.ThetaAuth;
import lombok.NonNull;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

class ThetaRequester {
    private static final MediaType mediaType = MediaType.parse("application/json");

    static JsonObject httpGet(@NonNull String url, @NonNull ThetaAuth auth) throws IOException, ApiException {
        String responseString = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(url),
            auth
        );

        return handleResponse(responseString);
    }

    static JsonObject httpPost(@NonNull String url, @NonNull JsonObject body, @NonNull ThetaAuth auth) throws IOException, ApiException {
        String responseString = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(url)
                .post(RequestBody.create(body.toString().getBytes(), mediaType)),
            auth
        );

        return handleResponse(responseString);
    }

    private static JsonObject handleResponse(String responseString) throws JsonParseException, ApiException {
        JsonObject response = Rson.DEFAULT.fromJson(responseString, JsonObject.class);

        String status = response.getString("status");
        JsonObject body = response.getObject("body");

        if (status.equalsIgnoreCase("ERROR")) {
            int errorCode = body.getNumber("error_code").intValue();
            String errorMessage = body.getString("error_message");

            String fullMessage = String.format("%s: %s", errorCode, errorMessage);
            switch (errorCode) {
                case 1000000:
                case 1010001:
                    throw new ApiAuthException(fullMessage);

                default:
                    throw new ApiException(fullMessage);
            }
        } else {
            return body;
        }
    }

}
