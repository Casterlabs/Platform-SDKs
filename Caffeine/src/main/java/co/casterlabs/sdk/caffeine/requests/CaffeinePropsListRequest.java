package co.casterlabs.sdk.caffeine.requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.caffeine.CaffeineApi;
import co.casterlabs.sdk.caffeine.CaffeineAuth;
import co.casterlabs.sdk.caffeine.CaffeineEndpoints;
import co.casterlabs.sdk.caffeine.HttpUtil;
import co.casterlabs.sdk.caffeine.types.CaffeineProp;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import okhttp3.Response;

public class CaffeinePropsListRequest extends AuthenticatedWebRequest<List<CaffeineProp>, CaffeineAuth> {

    public CaffeinePropsListRequest(CaffeineAuth auth) {
        super(auth);
    }

    @Override
    protected List<CaffeineProp> execute() throws ApiException, ApiAuthException, IOException {
        // Send empty json data, because it's required for some reason.
        try (Response response = HttpUtil.sendHttp("{}", CaffeineEndpoints.PROPS_LIST, this.auth, "application/json")) {
            String body = response.body().string();

            response.close();

            if (response.code() == 401) {
                throw new ApiAuthException("Auth is invalid: " + body);
            } else {
                JsonObject json = CaffeineApi.RSON.fromJson(body, JsonObject.class);
                JsonObject payload = json.getObject("payload");
                JsonObject digitalItems = payload.getObject("digital_items");
                JsonArray state = digitalItems.getArray("state");

                List<CaffeineProp> list = new ArrayList<>();

                for (JsonElement element : state) {
                    list.add(CaffeineProp.fromJson(element));
                }

                return list;
            }
        } catch (JsonParseException e) {
            throw new ApiException(e);
        }
    }

}
