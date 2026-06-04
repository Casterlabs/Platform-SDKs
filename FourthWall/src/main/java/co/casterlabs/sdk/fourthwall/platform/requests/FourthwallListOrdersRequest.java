package co.casterlabs.sdk.fourthwall.platform.requests;

import java.io.IOException;
import java.net.http.HttpResponse;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.PaginatedResponse;
import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.fourthwall.FourthwallAuth;
import co.casterlabs.sdk.fourthwall.platform.types.FourthwallOrder;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class FourthwallListOrdersRequest extends AuthenticatedWebRequest<PaginatedResponse<FourthwallOrder>, FourthwallAuth> {

    public FourthwallListOrdersRequest(@NonNull FourthwallAuth auth) {
        super(auth);
    }

    @Override
    protected PaginatedResponse<FourthwallOrder> execute() throws ApiException, ApiAuthException, IOException {
        return new PaginatedResponse<>(new _PaginationHelper<>() {
            @Override
            protected HttpResponse<JsonObject> request(int cursor) throws ApiAuthException, ApiException, IOException {
                QueryBuilder builder = new QueryBuilder()
                    .put("page", cursor)
                    .put("size", 20);

                return _RequestHelper.GET(
                    "https://api.fourthwall.com/open-api/v1.0/order?" + builder.toString(),
                    auth
                );
            }

            @Override
            protected Class<FourthwallOrder[]> generic() {
                return FourthwallOrder[].class;
            }
        });
    }

}
