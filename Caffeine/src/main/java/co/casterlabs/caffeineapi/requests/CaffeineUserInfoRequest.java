package co.casterlabs.caffeineapi.requests;

import java.io.IOException;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.caffeineapi.CaffeineApi;
import co.casterlabs.caffeineapi.CaffeineEndpoints;
import co.casterlabs.caffeineapi.HttpUtil;
import co.casterlabs.caffeineapi.types.CaffeineUser;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Setter
@Accessors(chain = true)
public class CaffeineUserInfoRequest extends WebRequest<CaffeineUser> {
    private @NonNull String query;

    public CaffeineUserInfoRequest setUsername(@NonNull String username) {
        this.query = username;
        return this;
    }

    public CaffeineUserInfoRequest setCAID(@NonNull String caid) {
        this.query = caid;
        return this;
    }

    @Override
    protected CaffeineUser execute() throws ApiException, IOException {
        assert this.query != null : "Query must be set.";

        String url = String.format(CaffeineEndpoints.USERS, this.query);

        try (Response response = HttpUtil.sendHttpGet(url, null)) {
            String body = response.body().string();

            if (response.code() == 404) {
                throw new ApiException("User does not exist: " + body);
            } else {
                JsonObject user = CaffeineApi.RSON.fromJson(body, JsonObject.class)
                    .getObject("user");

                return CaffeineUser.fromJson(user);
            }
        } catch (JsonParseException e) {
            throw new ApiException(e);
        }
    }

}
