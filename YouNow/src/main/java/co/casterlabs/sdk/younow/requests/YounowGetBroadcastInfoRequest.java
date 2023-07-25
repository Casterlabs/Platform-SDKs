package co.casterlabs.sdk.younow.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.younow.types.YounowChannel;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Request;

@Accessors(chain = true)
public class YounowGetBroadcastInfoRequest extends WebRequest<YounowChannel> {
    private @Setter String username;

    @Override
    protected YounowChannel execute() throws ApiException, ApiAuthException, IOException {
        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url("https://api.younow.com/php/api/broadcast/info/curId=0/user=" + this.username),
            null
        );

        JsonObject json = Rson.DEFAULT.fromJson(response, JsonObject.class);

        if (json.getNumber("errorCode").intValue() != 0) {
            throw new ApiException(json.getString("errorMsg"));
        }

        return Rson.DEFAULT.fromJson(json, YounowChannel.class);
    }

}
