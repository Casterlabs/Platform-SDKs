package co.casterlabs.sdk.dlive;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.NonNull;

public class DliveHttpUtil {

    public static JsonObject sendHttp(@NonNull String query, @Nullable DliveAuth auth) throws IOException, ApiException, ApiAuthException {
        HttpResponse<JsonObject> response = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(DliveApiJava.API))
                .POST(
                    BodyPublishers.ofString(
                        DliveApiJava
                            .formatQuery(query)
                    )
                )
                .header("Content-Type", "application/json"),
            RsonBodyHandler.of(JsonObject.class),
            auth
        );

        JsonObject responseJson = response.body();
        if (response.statusCode() == 401) {
            throw new ApiAuthException(responseJson.toString());
        } else if (responseJson.containsKey("errors")) {
            throw new ApiException(responseJson.toString());
        } else {
            return responseJson;
        }
    }

}
