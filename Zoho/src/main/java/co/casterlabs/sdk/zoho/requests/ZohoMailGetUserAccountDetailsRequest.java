package co.casterlabs.sdk.zoho.requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.zoho.ZohoAuth;
import co.casterlabs.sdk.zoho.ZohoHttpUtil;
import co.casterlabs.sdk.zoho.types.ZohoUserAccount;
import lombok.NonNull;
import okhttp3.Response;

public class ZohoMailGetUserAccountDetailsRequest extends AuthenticatedWebRequest<List<ZohoUserAccount>, ZohoAuth> {

    public ZohoMailGetUserAccountDetailsRequest(@NonNull ZohoAuth auth) {
        super(auth);
    }

    @Override
    protected List<ZohoUserAccount> execute() throws ApiException, ApiAuthException, IOException {
        try (Response response = ZohoHttpUtil.sendHttpGet("https://mail.zoho.com/api/accounts", null, this.auth)) {
            JsonObject json = Rson.DEFAULT.fromJson(response.body().string(), JsonObject.class);

            if (response.isSuccessful()) {
                JsonArray data = json.getArray("data");
                List<ZohoUserAccount> result = new ArrayList<>(data.size());

                for (JsonElement e : data) {
                    result.add(Rson.DEFAULT.fromJson(e, ZohoUserAccount.class));
                }

                return result;
            } else {
                throw new ApiException(json.toString());
            }
        }
    }

}
