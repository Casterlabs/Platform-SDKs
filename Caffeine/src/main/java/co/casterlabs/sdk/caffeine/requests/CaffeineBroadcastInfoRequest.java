package co.casterlabs.sdk.caffeine.requests;

import java.io.IOException;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.caffeine.CaffeineApi;
import co.casterlabs.sdk.caffeine.CaffeineEndpoints;
import co.casterlabs.sdk.caffeine.HttpUtil;
import co.casterlabs.sdk.caffeine.types.CaffeineBroadcast;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Setter
@Accessors(chain = true)
public class CaffeineBroadcastInfoRequest extends WebRequest<CaffeineBroadcast> {
    private @NonNull String broadcastId;

    @Override
    protected CaffeineBroadcast execute() throws ApiException, IOException {
        assert this.broadcastId != null : "Broadcast ID must be set.";

        String url = String.format(CaffeineEndpoints.BROADCASTS, this.broadcastId);

        try (Response response = HttpUtil.sendHttpGet(url, null)) {
            String body = response.body().string();

            if (response.code() == 404) {
                throw new ApiException("Broadcast does not exist: " + body);
            } else {
                JsonObject user = CaffeineApi.RSON.fromJson(body, JsonObject.class)
                    .getObject("broadcast");

                return CaffeineBroadcast.fromJson(user);
            }
        } catch (JsonParseException e) {
            throw new ApiException(e);
        }
    }

}
