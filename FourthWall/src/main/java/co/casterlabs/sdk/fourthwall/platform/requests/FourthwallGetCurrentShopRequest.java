package co.casterlabs.sdk.fourthwall.platform.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.fourthwall.FourthwallAuth;
import co.casterlabs.sdk.fourthwall.platform.types.FourthwallShop;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class FourthwallGetCurrentShopRequest extends AuthenticatedWebRequest<FourthwallShop, FourthwallAuth> {

    public FourthwallGetCurrentShopRequest(@NonNull FourthwallAuth auth) {
        super(auth);
    }

    @Override
    protected FourthwallShop execute() throws ApiException, ApiAuthException, IOException {
        HttpResponse<JsonObject> response = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create("https://api.fourthwall.com/open-api/v1.0/shops/current"))
                .GET(),
            RsonBodyHandler.of(JsonObject.class),
            this.auth
        );

        if (response.statusCode() < 200 && response.statusCode() > 299) {
            throw new ApiException(response.body().toString());
        }

        return Rson.DEFAULT.fromJson(response.body(), FourthwallShop.class);
    }

}
